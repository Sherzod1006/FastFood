package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.Branch;
import uz.pdp.apporder.entity.ClientAddress;
import uz.pdp.apporder.entity.Order;
import uz.pdp.apporder.entity.OrderProduct;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PaymentType;
import uz.pdp.apporder.entity.promotion.*;
import uz.pdp.apporder.exceptions.RestException;
import uz.pdp.apporder.payload.*;
import uz.pdp.apporder.payload.promotion.AcceptPromotionDTO;
import uz.pdp.apporder.payload.promotion.OrderWithPromotionDTO;
import uz.pdp.apporder.repository.*;
import uz.pdp.appproduct.aop.AuthFeign;
import uz.pdp.appproduct.dto.ClientDTO;
import uz.pdp.appproduct.dto.EmployeeDTO;
import uz.pdp.appproduct.entity.Product;
import uz.pdp.appproduct.repository.ProductRepository;
import uz.pdp.appproduct.util.CommonUtils;
import uz.pdp.appproduct.util.RestConstants;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final ClientRepository clientRepository;
    private final AuthFeign openFeign;
    private final BranchService branchService;
    private final PriceForDeliveryRepository priceForDeliveryRepository;

    private final PromotionsService promotionsService;
    private final DiscountService discountService;

    private final PromotionRepository promotionRepository;

    @Override
    public ApiResult<?> saveOrder(OrderUserDTO orderDTO) {

        ClientDTO currentClient = CommonUtils.getCurrentClient();

        saveOrder(new OrderWebDTO(null,
                        orderDTO.getOrderProductsDTOList(),
                        orderDTO.getAddressDTO(),
                        orderDTO.getPaymentType()),
                currentClient.getUserId(),
                null, new Order());

        return ApiResult.successResponse("Order successfully saved!");
    }

    /**
     * Kurier ning bir sanadagi zakazlari ro'yxati
     * @param id = @id
     * @param localDate = @localDate
     * @return List<OrderForCurrierDTO>
     */
    @Override
    public ApiResult<List<OrderForCurrierDTO>> getOrdersForCurrierByOrderedDate(UUID id, LocalDateTime localDate) {
        List<Order> ordersList = orderRepository.findAllByCurrierIdAndOrderedAtOrderByOrderedAtDesc(id, localDate).orElseThrow(() -> RestException.restThrow("Bu sanada hech qanaqa zakaz bo'lmagan", HttpStatus.NOT_FOUND));
        List<OrderForCurrierDTO> orderForCurrierDTOList = ordersList.stream().map(this::mapOrderToOrderForHistory).collect(Collectors.toList());
        return ApiResult.successResponse("All orders of Currier in this Date ", orderForCurrierDTOList);
    }

    /**
     * Kurierning barcha zakazlari
     * @param id = @id
     * @return List<OrderForCurrierDTO>
     */
    @Override
    public ApiResult<List<OrderForCurrierDTO>> getAllOrdersForCurrier(UUID id) {
        List<Order> orders = orderRepository.findAllByCurrierIdOrderByOrderedAtDesc(id).orElseThrow(() -> RestException.restThrow("Bu currierning zakazlari yo'q", HttpStatus.NOT_FOUND));
        List<OrderForCurrierDTO> orderForCurrierDTOList = orders.stream().map(this::mapOrderToOrderForHistory).collect(Collectors.toList());
        return ApiResult.successResponse("Currier ning barcha zakazlari ro'yxati", orderForCurrierDTOList);
    }

    @Override
    public ApiResult<OrderWithPromotionDTO> getOrderPromotions(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> RestException.restThrow("ORDER NOT FOUND", HttpStatus.NOT_FOUND));

        Float overAllSum = order.getOverAllSum();
        List<OrderProduct> orderProducts = order.getOrderProducts();
        Float deliverySum = order.getDeliverySum();

        Promotion promotion = promotionRepository.get1ActivePromotion(System.currentTimeMillis())
                .orElse(null);


        if (Objects.nonNull(promotion)) {
            DiscountPromotion discountPromotion = promotion.getDiscountPromotion();
            DeliveryPromotion deliveryPromotion = promotion.getDeliveryPromotion();
            BonusProductPromotion bonusProductPromotion = promotion.getBonusProductPromotion();

            if (Objects.nonNull(deliveryPromotion)) {
                Float moreThan = deliveryPromotion.getMoreThan();
                Long startTime = deliveryPromotion.getStartTime();
                Long endTime = deliveryPromotion.getEndTime();
                long now = System.currentTimeMillis();
                if (overAllSum > moreThan) {
                    if (now > startTime && now < endTime) {
                        deliverySum = 0F;
                    }
                }
            }

            if (Objects.nonNull(bonusProductPromotion)) {
                Float moreThan = bonusProductPromotion.getMoreThan();
                Short bonusCount = bonusProductPromotion.getBonusCount();
                Product product = bonusProductPromotion.getProduct();

                if (overAllSum > moreThan) {
                    orderProducts.add(new OrderProduct(order, product, bonusCount, product.getPrice()));
                }
            }

            if (Objects.nonNull(discountPromotion)) {
                Float moreThan = discountPromotion.getMoreThan();
                Float discount = discountPromotion.getDiscount();
                if (overAllSum > moreThan) {
                    overAllSum = overAllSum - (overAllSum * discount / 100);
                }
            }
            order.setOverAllSum(overAllSum);
            order.setDeliverySum(deliverySum);
            order.setOrderProducts(orderProducts);
        }

        return ApiResult.successResponse(
                new OrderWithPromotionDTO(mapOrderToOrderDTO(order),
                        promotionsService.promotionToPromotionDTO(promotion)
                )
        );
    }

    @Override
    public ApiResult<?> saveOrder(OrderWebDTO orderDTO) {

        EmployeeDTO currentEmployee = (EmployeeDTO) CommonUtils
                .getCurrentRequest().getAttribute("currentUser");

        ClientDTO clientDTO = Objects.requireNonNull(
                openFeign.getClientDTOAndSet(orderDTO.getClient(),
                        CommonUtils.getCurrentRequest().getHeader("Authorization")).getData()
        );

        saveOrder(orderDTO,
                clientDTO.getUserId(),
                currentEmployee.getId(), null);

        return ApiResult.successResponse("Order successfully saved!");
    }


    private void saveOrder(OrderWebDTO orderDTO, UUID clientId, UUID operatorId, Order order) {

        // TODO: 9/27/22 Filial Id ni aniqlash
        Branch branch = findNearestBranch(orderDTO.getAddress());


        // TODO: 9/28/22 kardinatalardan shipping narxini xisoblash
        Float shippingPrice = findShippingPrice(branch, orderDTO.getAddress());

        ClientAddress clientAddress = new ClientAddress(orderDTO.getAddress().getLatitude(),
                orderDTO.getAddress().getLongitude(),
                orderDTO.getAddress().getAddress(),
                orderDTO.getAddress().getExtraAddress());


        List<Product> productList = productRepository.findAllById(
                orderDTO
                        .getProducts()
                        .stream()
                        .map(OrderProductDTO::getProductId)
                        .collect(Collectors.toList())
        );

        List<OrderProduct> orderProducts =
                productList
                        .stream()
                        .map(product -> mapOrderProduct(orderDTO, order, product))
                        .collect(Collectors.toList());

        if (orderDTO.getPaymentType().name().equals(PaymentType.CASH.name())
                || orderDTO.getPaymentType().name().equals(PaymentType.TERMINAL.name()))
            order.setStatusEnum(OrderStatusEnum.NEW);
        else
            order.setStatusEnum(OrderStatusEnum.PAYMENT_WAITING);

        Float overallSum = calculateProductsSum(order);

        order.setBranch(branch);
        order.setPaymentType(orderDTO.getPaymentType());
        order.setClientId(clientId);
        order.setOperatorId(operatorId);
        order.setOrderProducts(orderProducts);
        order.setDeliverySum(shippingPrice);
        order.setAddress(clientAddress);

        clientRepository.save(clientAddress);
        orderRepository.save(order);

    }


    @Override
    public ApiResult<List<OrderDTO>> getOrdersByStatus(String orderStatus) {

        List<Order> orders = orderRepository.findByStatusEnum(OrderStatusEnum.valueOf(orderStatus));

        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO orderDto = mapOrderToOrderDTO(order);
            orderDTOList.add(orderDto);
        }
        return ApiResult.successResponse(orderDTOList);
    }

    @Override
    public ApiResult<List<OrderDTO>> getOrders() {
        List<Order> orders = orderRepository.findAll();

        List<OrderDTO> orderDtoList = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO orderDto = mapOrderToOrderDTO(order);
            orderDtoList.add(orderDto);
        }
        return ApiResult.successResponse(orderDtoList);

    }

    @Override
    public ApiResult<?> getOrderForCourier(OrderStatusEnum orderStatusEnum) {
        if (!(orderStatusEnum == OrderStatusEnum.SENT || orderStatusEnum == OrderStatusEnum.READY))
            throw RestException.restThrow("status must be sent or ready", HttpStatus.BAD_REQUEST);

        return ApiResult.successResponse(getOrdersByStatus(orderStatusEnum));
    }

    @Override
    public ApiResult<OrderDTO> getOneOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("order not found", HttpStatus.NOT_FOUND));
        OrderDTO orderDto = mapOrderToOrderDTO(order);
        return ApiResult.successResponse(orderDto);
    }

    @Override
    public ApiResult<OrderStatusWithCountAndPrice> getOrderStatusCountPrice(OrderStatusEnum orderStatus) {
        int count = 0;
        Double price = 0d;
        for (Order order : orderRepository.findByStatusEnum(orderStatus)) {
            count++;
            Double aDouble = orderProductRepository.countSumOfOrder(order.getId());
            price += aDouble;
        }
        OrderStatusWithCountAndPrice orderStatusWithCountAndPrice = new OrderStatusWithCountAndPrice();

        orderStatusWithCountAndPrice.setCount(count);
        orderStatusWithCountAndPrice.setPrice(price);
        orderStatusWithCountAndPrice.setStatusEnum(orderStatus);
        return ApiResult.successResponse(orderStatusWithCountAndPrice);
    }

    @Override
    public ApiResult<?> editOrder(OrderWebDTO newOrder, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> RestException
                .restThrow("No such Order", HttpStatus.NOT_FOUND));

        EmployeeDTO currentEmployee = (EmployeeDTO) CommonUtils
                .getCurrentRequest().getAttribute("currentUser");

        ClientDTO clientDTO = Objects.requireNonNull(
                openFeign.getClientDTOAndSet(newOrder.getClient(),
                        CommonUtils.getCurrentRequest().getHeader("Authorization")).getData()
        );

        saveOrder(newOrder, clientDTO.getUserId(), currentEmployee.getId(), order);

        return ApiResult.successResponse("Order successfully updated");
    }

    @Override
    public ApiResult<OrderDTO> acceptOrderPromotion(AcceptPromotionDTO acceptPromotionDTO) {
        Long promotionId = acceptPromotionDTO.getPromotionId();
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> RestException.restThrow("PROMOTION_NOT_FOUND", HttpStatus.NOT_FOUND));

        Long orderId = acceptPromotionDTO.getOrderId();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> RestException.restThrow("ORDER_NOT_FOUND", HttpStatus.NOT_FOUND));

        if (acceptPromotionDTO.isProductPromotion()) {

            ProductPromotion productPromotion = promotion.getProductPromotion();

            Product product = productPromotion
                    .getBonusProducts()
                    .stream()
                    .filter(p -> p.getId().equals(acceptPromotionDTO.getChosenProductId()))
                    .findFirst()
                    .orElseThrow(() -> RestException.restThrow("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND));


            if (Objects.nonNull(product)) {
                List<OrderProduct> orderProducts = order.getOrderProducts();

                orderProducts.add(new OrderProduct(order, product, (short) 1, product.getPrice()));
                order.setOrderProducts(orderProducts);
            }
        }

        return ApiResult.successResponse(mapOrderToOrderDTO(order));

    }


    // TODO: 10/3/22 Eng yaqin branchni aniqlash

    /**
     * Eng yaqin Branchgacha bo'lgan kesma uzunligi o'lchangan,
     * Yandex yoki Google mapni api ni
     * olib kelish kerak va eng yaqin
     * branchga yo'lni topib uzunligini aniqlash kerak!
     *
     * @param location
     * @return Branch
     */
    private Branch findNearestBranch(AddressDTO location) {
        Branch chosenBranch = null;
        if (Objects.nonNull(location)) {
            List<Branch> branches = branchService.getAll().getData();
            Double distance = null;
            for (Branch branch : branches)
                if (Objects.isNull(distance)) {
                    distance = getDistance(branch, location);
                    chosenBranch = branch;
                } else if (distance < getDistance(branch, location)) {
                    distance = getDistance(branch, location);
                    chosenBranch = branch;
                }
        }
        return chosenBranch;
    }

    private Double getDistance(Branch branch, AddressDTO location) {
        return Math.sqrt(
                Math.pow(branch.getAddress().getLat() - location.getLatitude(), 2)
                        + Math.pow(branch.getAddress().getLon() - location.getLongitude(), 2)
        );
    }

    // TODO: 10/3/22  shipping narxini aniqlash
    private Float findShippingPrice(Branch branch, AddressDTO addressDTO) {
        branch = findNearestBranch(addressDTO);

        Double distance = getDistance(branch, addressDTO);

        var priceForDelivery = priceForDeliveryRepository.findByBranch(branch).orElseThrow(() -> RestException.restThrow("No such branch price for delivery", HttpStatus.NOT_FOUND));

        if (priceForDelivery.getPriceForPerKilometre() <= distance) {
            return priceForDelivery.getPriceForPerKilometre();
        }

        var shippingPrice = priceForDelivery.getInitialPrice() + priceForDelivery.getInitialDistance();
        return shippingPrice;
    }

    private OrderDTO mapOrderToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setNumber(order.getNumber());
        orderDTO.setPaymentType(order.getPaymentType());
        orderDTO.setDeliverySum(order.getDeliverySum());
        orderDTO.setBranchName(order.getBranch().getName());

        orderDTO.setOrderedAt(order.getOrderedAt());
        setOrderTimeByStatus(order, orderDTO);

        orderDTO.setProductsSum(calculateProductsSum(order));

        orderDTO.setDiscountSum(calculateDiscountSumForOrder(order));

        String token = CommonUtils.getCurrentRequest().getHeader(RestConstants.AUTHORIZATION_HEADER);

        orderDTO.setClientDTO(Objects.requireNonNull(openFeign.getClientDTO(order.getClientId(), token).getData()));

        if (Objects.nonNull(order.getOperatorId()))
            orderDTO.setOperatorDTO(Objects.requireNonNull(openFeign.getEmployeeDTO(order.getOperatorId(), token).getData()));

        if (Objects.nonNull(order.getCurrierId())) {
            orderDTO.setCurrierDTO(Objects.requireNonNull(openFeign.getCurrierDTO(order.getCurrierId(), CommonUtils.getCurrentRequest().getHeader("Authorization")).getData()));
        }
        return orderDTO;
    }

    private Float calculateDiscountSumForOrder(Order order) {

        List<Integer> collect = order.getOrderProducts()
                .stream()
                .map(OrderProduct::getId)
                .collect(Collectors.toList());

        return discountService.getDiscountsSumOfProducts(collect).orElse(0F);
    }

    private Float calculateProductsSum(Order order) {
        double sum = order
                .getOrderProducts()
                .stream()
                .mapToDouble(value -> value.getUnitPrice() * value.getQuantity())
                .sum();
        return (float) sum;
    }

    private void setOrderTimeByStatus(Order order, OrderDTO orderDTO) {
        if (order.getStatusEnum() == OrderStatusEnum.PAYMENT_WAITING
                || order.getStatusEnum() == OrderStatusEnum.NEW) {
            orderDTO.setOrderedAtByStatus(order.getOrderedAt());
        } else if (order.getStatusEnum() == OrderStatusEnum.ACCEPTED) {
            orderDTO.setOrderedAtByStatus(order.getAcceptedAt());
        } else if (order.getStatusEnum() == OrderStatusEnum.COOKING) {
            orderDTO.setOrderedAtByStatus(order.getCookingAt());
        } else if (order.getStatusEnum() == OrderStatusEnum.READY) {
            orderDTO.setOrderedAtByStatus(order.getReadyAt());
        } else if (order.getStatusEnum() == OrderStatusEnum.SENT) {
            orderDTO.setOrderedAtByStatus(order.getSentAt());
        } else if (order.getStatusEnum() == OrderStatusEnum.FINISHED) {
            orderDTO.setOrderedAtByStatus(order.getClosedAt());
        } else if (order.getStatusEnum() == OrderStatusEnum.REJECTED) {
            orderDTO.setOrderedAtByStatus(order.getCancelledAt());
        }
    }


    private List<Order> getOrdersByStatus(OrderStatusEnum statusEnum) {
        return orderRepository.getOrderByStatusEnum(statusEnum);
    }


    private OrderProduct mapOrderProduct(OrderWebDTO orderDTO, Order order, Product product) {
        return new OrderProduct(
                order,
                product,
                orderDTO
                        .orderProductDTOMap()
                        .get(product.getId())
                        .getQuantity(),
                product.getPrice());
    }

    public OrderForCurrierDTO mapOrderToOrderForHistory(Order order) {
        OrderForCurrierDTO orderForCurrierDTO = new OrderForCurrierDTO();

        Float totalProductsPrice = calculateProductsSum(order);

        orderForCurrierDTO.setOrderNumber(order.getNumber());

        orderForCurrierDTO.setBranchName(order.getBranch().getName());

        orderForCurrierDTO.setOrderedDate(order.getOrderedAt().toLocalDate());

        orderForCurrierDTO.setProductsSum(totalProductsPrice);

        orderForCurrierDTO.setDeliverySum(order.getDeliverySum());

        orderForCurrierDTO.setPaymentType(order.getPaymentType());

        orderForCurrierDTO.setClientAddress(order.getAddress().getAddress());

        return orderForCurrierDTO;

    }
}