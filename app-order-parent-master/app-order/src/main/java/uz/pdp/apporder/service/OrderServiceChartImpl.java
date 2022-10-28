package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.Order;
import uz.pdp.apporder.entity.OrderProduct;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PaymentType;
import uz.pdp.apporder.repository.BranchRepository;
import uz.pdp.apporder.repository.OrderProductRepository;
import uz.pdp.apporder.repository.OrderRepository;
import uz.pdp.apporder.exceptions.RestException;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderChartDTO;
import uz.pdp.apporder.payload.OrderChartPaymentDTO;
import uz.pdp.apporder.payload.OrderStatisticsChartDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;


@Service
@RequiredArgsConstructor
public class OrderServiceChartImpl implements OrderServiceChart {
    private final BranchRepository branchRepository;

    private final OrderRepository orderRepository;

    private final OrderProductRepository orderProductRepository;

    /**
     * Tegilmasin
     * <p>Show Statistics for admin with chart diagram</p>
     *
     */
    public ApiResult<OrderStatisticsChartDTO> getStatisticsOrder(OrderChartDTO orderChartDTO) {

        checkOrderChartDTO(
                orderChartDTO.getBranchId(),
                orderChartDTO.getTillDate(),
                orderChartDTO.getFromDate(),
                orderChartDTO.getOrderStatusEnum()
                );

        Map<?, Integer> map = countingOrderByStatusAndDate(orderChartDTO);

        return ApiResult.successResponse(mapOrderChartDTOToOrderStatisticsDTO(orderChartDTO, map));
    }


    /**
     * depend on counting
     */
    private void countingByMonth(OrderChartDTO orderChartDTO,
                                 LocalDate fromDate,
                                 LocalDate tillDate,
                                 Map<Month, Integer> monthMap){

        Set<Month> set = new HashSet<>();
        int count = 0;
        while (fromDate.isBefore(tillDate)){
            set.add(fromDate.getMonth());

            if (!set.contains(fromDate.getMonth())){
                monthMap.put(fromDate.minusDays(1).getMonth(),count);
                count = 0;
            }

            count += getCountByDay(orderChartDTO, fromDate);
            fromDate = fromDate.plusDays(1);

        }
    }

    /**
     * show payment statistics by payment
     */
    @Override
    public ApiResult<OrderStatisticsChartDTO> getStatisticsPayment(OrderChartPaymentDTO orderChartPaymentDTO) {

        checkOrderChartDTO(orderChartPaymentDTO.getBranchId(),
                orderChartPaymentDTO.getTillDate(),
                orderChartPaymentDTO.getFromDate(),
                null);

        return ApiResult.successResponse(sumPayment(orderChartPaymentDTO));
    }

    private OrderStatisticsChartDTO sumPayment(OrderChartPaymentDTO orderChartPaymentDTO){

        LocalDate fromDate = orderChartPaymentDTO.getFromDate();
        LocalDate tillDate = orderChartPaymentDTO.getTillDate();

        double paymentPayme = 0D;
        double paymentClick= 0D;
        double paymentChash = 0D;
        double paymentTerminal = 0D;

        while (fromDate.atStartOfDay().isBefore(tillDate.atStartOfDay())){

            PaymentType[] values = PaymentType.values();

            for (PaymentType value : values) {
                double sumByPaymentType = getSumByPaymentType(value, fromDate);

                if (value.equals(PaymentType.CASH))
                    paymentChash += sumByPaymentType;
                else if (value.equals(PaymentType.PAYME))
                    paymentPayme += sumByPaymentType;
                else if (value.equals(PaymentType.CLICK))
                    paymentClick += sumByPaymentType;
                else
                    paymentTerminal += sumByPaymentType;

            }
            fromDate = fromDate.plusDays(1);

        }

        double totalPayment= paymentChash+paymentPayme+paymentClick+paymentTerminal;

        return returnStatisticsChartDTO(
                paymentChash, paymentPayme,
                paymentClick, paymentTerminal, totalPayment
        );
    }

    private double getSumByPaymentType(PaymentType paymentType, LocalDate fromDate){
        double payment = 0;

        List<Order> orderByDate = orderRepository.findAllByClosedAtGreaterThanEqualAndClosedAtLessThanEqual(
                LocalDateTime.from(fromDate.atStartOfDay()),
                LocalDateTime.from(fromDate.plusDays(1).atStartOfDay())
        );

        for (Order order : orderByDate) {
            if (Objects.equals(order.getPaymentType(), paymentType)){
                OrderProduct orderProduct = orderProductRepository.findByOrderId(order.getId());
                payment+=orderProduct.getQuantity()*orderProduct.getUnitPrice();
            }
        }

        return payment;

    }

    /**
     * count order by day
     */
    private int getCountByDay(OrderChartDTO orderChartDTO,
                              LocalDate fromDate) {
        int count = 0;

        List<Order> all = orderRepository.findAllByClosedAtGreaterThanEqualAndClosedAtLessThanEqual(
                LocalDateTime.from(fromDate.atStartOfDay()),
                LocalDateTime.from(fromDate.plusDays(1).atStartOfDay())
        );

        boolean rejected = orderChartDTO.getOrderStatusEnum().equals(OrderStatusEnum.REJECTED);
        boolean aNull = Objects.isNull(orderChartDTO.getBranchId());

        for (Order order : all) {
            if (rejected) {
                if (aNull)
                    count++;
                else if (Objects.equals(order.getBranch().getId(), orderChartDTO.getBranchId()))
                    count++;
            } else
                count++;
        }
        return count;
    }

    /**
     *
     * Tegilmasin
     * Bu getStatisticsForChart methodi qismi
     *
     */
    private void checkOrderChartDTO(
            Integer branchId,
            LocalDate tillDate,
            LocalDate fromDate,
            OrderStatusEnum orderStatusEnum
    ) {

        if (!Objects.isNull(branchId) && !branchRepository.existsById(branchId))
            throw RestException.restThrow("Bunday filial mavjud emas!", HttpStatus.NOT_FOUND);

        if (tillDate.isBefore(fromDate))
            throw RestException.restThrow("Vaqtlar no'togri berilgan!", HttpStatus.BAD_REQUEST);

        if (tillDate.isAfter(LocalDate.now()))
            throw RestException.restThrow("Kelajakda nima bo'lishini xudo biladi!", HttpStatus.BAD_REQUEST);

        if (!Objects.isNull(orderStatusEnum)&&!orderStatusEnum.equals(OrderStatusEnum.FINISHED)
                && !orderStatusEnum.equals(OrderStatusEnum.REJECTED))
            throw RestException.restThrow("Faqat Rejected va Finished statuslari uchungina statistica mavjud!"
                    , HttpStatus.NOT_FOUND);
    }


    /**
     * Bu getStatisticsForChart method qismi
     *
     */
    private Map<?,Integer> countingOrderByStatusAndDate(OrderChartDTO orderChartDTO) {

        LocalDate fromDate = orderChartDTO.getFromDate();
        LocalDate tillDate = orderChartDTO.getTillDate();

        if (fromDate.plusDays(30).atStartOfDay().isBefore(tillDate.atStartOfDay())) {
            Map<Month,Integer> monthMap = new TreeMap<>();
            countingByMonth(orderChartDTO,fromDate,tillDate, monthMap);
            return monthMap;
        }
        else {
            Map<LocalDate,Integer> dayMap = new TreeMap<>();
            while (tillDate.atStartOfDay().isAfter(fromDate.atStartOfDay())) {
                dayMap.put(fromDate,getCountByDay(orderChartDTO, fromDate));
                fromDate = fromDate.plusDays(1);
            }
            return dayMap;
        }
    }



    private static OrderStatisticsChartDTO mapOrderChartDTOToOrderStatisticsDTO(OrderChartDTO orderChartDTO,
                                                                                Map<?, Integer> map) {
        return new OrderStatisticsChartDTO(
                map, orderChartDTO.getBranchId(),
                orderChartDTO.getFromDate(),
                orderChartDTO.getTillDate(),
                orderChartDTO.getOrderStatusEnum()
        );
    }

    private OrderStatisticsChartDTO returnStatisticsChartDTO(double paymentChash,
                                                             double paymentPayme, double paymentClick,
                                                             double paymentTerminal, double totalPayment) {

        return new OrderStatisticsChartDTO(
                paymentChash,paymentPayme,
                paymentClick, paymentTerminal,totalPayment
        );
    }
}