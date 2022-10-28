package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.Branch;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PaymentType;
import uz.pdp.apporder.exceptions.RestException;
import uz.pdp.apporder.payload.*;
import uz.pdp.apporder.projection.StatisticsOrderDTOProjection;
import uz.pdp.apporder.repository.BranchRepository;
import uz.pdp.apporder.repository.OrderProductRepository;
import uz.pdp.apporder.repository.OrderRepository;
import uz.pdp.appproduct.aop.AuthFeign;
import uz.pdp.appproduct.aop.CheckAuthEmpl;
import uz.pdp.appproduct.dto.ClientDTO;
import uz.pdp.appproduct.dto.EmployeeDTO;
import uz.pdp.appproduct.dto.enums.PermissionEnum;
import uz.pdp.appproduct.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderStatisticsInListImpl implements OrderStatisticsInList {

    private final OrderRepository orderRepository;
    private final BranchRepository branchRepository;
    private final OrderProductRepository orderProductRepository;

    private final AuthFeign openFeign;


    /**
     * <p>Show Statistics for admin with list</p>
     *
     * @param viewDTO for filtering, searching and sorting
     * @param page    for pagination
     * @param size    for pagination
     * @return List of OrderStatisticsDTO
     */
    @CheckAuthEmpl(permissions = PermissionEnum.SHOW_STATISTICS_FOR_LIST)
    @Override
    public ApiResult<List<OrderStatisticsDTO>> getStatisticsForList(ViewDTO viewDTO, int page, int size) {

        StringBuilder query = new StringBuilder("SELECT b.id as branchId, o.id as orderId,  Cast(o.client_id as varchar) as clientId, Cast(o.operator_id as varchar) as operatorId, o.payment_type as paymentType,  o.status_enum as statusEnum , o.ordered_at as orderedAt \n" +
                "FROM orders o\n" +
                "         JOIN branch b on b.id = o.branch_id\n"
        );

        if (Objects.nonNull(viewDTO)) {

            OrderListDTO orderListDTO = viewDTO.getOrderListDTO();
            SearchingDTO searchingDTO = viewDTO.getSearching();
            List<SortingDTO> sortingDTOS = viewDTO.getSorting();

            boolean hasFilterWorked = filter(query, orderListDTO);

            search(query, searchingDTO, hasFilterWorked);

            sortByRequest(query, sortingDTOS);

        }

        sortDefault(viewDTO, query);

        query.append("\n LIMIT ").append(size).append(" OFFSET ").append((page - 1) * size);

        List<StatisticsOrderDTOProjection> ordersByStringQuery = orderRepository.getOrdersByStringQuery(query.toString());

        List<OrderStatisticsDTO> orderStatisticsDTOList = new ArrayList<>();

        for (StatisticsOrderDTOProjection projection : ordersByStringQuery) {
            OrderStatisticsDTO orderStatisticsDTO = mapProjectionToOrderStatisticsDTO(projection);
            orderStatisticsDTOList.add(orderStatisticsDTO);
        }

        return ApiResult.successResponse(orderStatisticsDTOList);
    }

    /**
     * this method sorts the list of orders according to entered values
     *
     * @param query       to write query for native query
     * @param sortingDTOS sorting values
     */
    private void sortByRequest(StringBuilder query, List<SortingDTO> sortingDTOS) {
        if (sortingDTOS.size() > 0) {
            query
                    .append("\n")
                    .append("ORDER BY ");

            for (SortingDTO sortingDTO : sortingDTOS) {
                query
                        .append(sortingDTO.getName())
                        .append(" ")
                        .append(sortingDTO.getType());
            }
        }
    }

    private void sortDefault(ViewDTO viewDTO, StringBuilder query) {
        if (viewDTO.getSorting().size() == 0) {
            query
                    .append("\n")
                    .append("ORDER BY ")
                    .append(" ordered_at ")
                    .append(" DESC ");
        }
    }

    private void search(StringBuilder query, SearchingDTO searchingDTO, boolean hasFilterWorked) {
        if (searchingDTO.getColumns().size() > 0) {
            if (!hasFilterWorked)
                query.append(" WHERE ");
            else
                query.append("  AND ( ");

            int columnSize = 1;
            for (String column : searchingDTO.getColumns()) {
                query
                        .append(column)
                        .append(" ilike '%")
                        .append(searchingDTO.getValue())
                        .append("%' ");
                if (columnSize < searchingDTO.getColumns().size())
                    query.append(" OR");
            }
            if (hasFilterWorked)
                query.append(" ) ");
        }
    }

    private boolean filter(StringBuilder query, OrderListDTO orderListDTO) {
        boolean hasFilterWorked = false;
        if (Objects.nonNull(orderListDTO)) {
            String branchName = orderListDTO.getBranchName();
            PaymentType paymentType = orderListDTO.getPaymentType();
            OrderStatusEnum orderStatusEnum = orderListDTO.getOrderStatusEnum();

            if (!Objects.isNull(branchName) || !Objects.isNull(paymentType) || !Objects.isNull(orderStatusEnum))
                query.append(" WHERE ");

            boolean hasBranchName = false;
            boolean hasPaymentType = false;
            if (!Objects.isNull(branchName)) {
                query.append(" b.name = ")
                        .append(" ' ")
                        .append(branchName)
                        .append(" ' ");
                hasBranchName = true;
                hasFilterWorked = true;
            }

            if (hasBranchName)
                query.append(" AND ");

            if (!Objects.isNull(paymentType)) {
                query.append(" o.payment_type = ")
                        .append(" ' ")
                        .append(paymentType)
                        .append(" ' ");
                hasPaymentType = true;
                hasFilterWorked = true;
            }

            if (hasPaymentType)
                query.append(" AND ");

            if (!Objects.isNull(orderStatusEnum)) {
                query.append(" o.status_enum = ")
                        .append(" ' ")
                        .append(orderStatusEnum)
                        .append(" ' ");
                hasFilterWorked = true;
            }
        }
        return hasFilterWorked;
    }

    private OrderStatisticsDTO mapProjectionToOrderStatisticsDTO(StatisticsOrderDTOProjection projection) {
        OrderStatisticsDTO orderStatisticsDTO = new OrderStatisticsDTO();
        Branch branch = branchRepository.findById(projection.getBranchId()).orElseThrow(
                () -> RestException.restThrow("branch not found", HttpStatus.NOT_FOUND)
        );

        BranchDTO branchDTO = BranchDTO.mapBranchToBranchDTO(branch);

        Double totalSumOfOrder = orderProductRepository.countSumOfOrder(projection.getOrderId());

        orderStatisticsDTO.setBranchDTO(branchDTO);

        String token = CommonUtils.getCurrentRequest().getHeader("Authorization");

        ClientDTO clientDTO = openFeign.getClientDTO(UUID.fromString(projection.getClientId()), token).getData();

        EmployeeDTO operatorDTO = openFeign.getEmployeeDTO(UUID.fromString(projection.getOperatorId()), token).getData();

        orderStatisticsDTO.setClientDTO(clientDTO);
        orderStatisticsDTO.setOperatorDTOForList(mapOperatorDTOToOperatorDTOForList(operatorDTO));
        orderStatisticsDTO.setSum(totalSumOfOrder);
        orderStatisticsDTO.setOrderedAt(projection.getOrderedAt());
        orderStatisticsDTO.setStatusEnum(projection.getStatusEnum());
        orderStatisticsDTO.setPaymentType(projection.getPaymentType());

        return orderStatisticsDTO;
    }

    private OperatorDTOForList mapOperatorDTOToOperatorDTOForList(EmployeeDTO operatorDTO) {
        return new OperatorDTOForList(operatorDTO.getFirstName(), operatorDTO.getLastName());
    }
}