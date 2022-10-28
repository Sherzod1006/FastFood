package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.pdp.entity.Client;
import uz.pdp.entity.User;
import uz.pdp.exceptions.RestException;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.add_DTO.AddClientDTO;
import uz.pdp.payload.response_DTO.ClientDTO;
import uz.pdp.payload.filterPayload.*;
import uz.pdp.payload.filterPayload.enums.FilterTypeEnum;
import uz.pdp.repository.ClientRepository;
import uz.pdp.repository.UserRepository;
import uz.pdp.util.SearchingColumnsEnum;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final RestTemplate restTemplate;

    private final ClientRepository clientRepository;

    private final UserRepository userRepository;
    private final UserService userService;

    private final String ORDER_SERVICE_URL = "http://localhost/api/order/v1/order/users-order-count";


    @Override
    public ApiResult<List<ClientDTO>> getAll() {
        List<Client> all = clientRepository.findAll();
        return ApiResult.successResponse(all
                .stream()
                .map(ClientDTO::mapping)
                .collect(
                Collectors.toList()));
    }

    @Override
    public ApiResult<ClientDTO> get(UUID id) {

        return ApiResult.successResponse(ClientDTO.mapping(clientRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("", HttpStatus.NOT_FOUND))));
    }

    @Override
    public ApiResult<Boolean> add(AddClientDTO addClientDTO) {
        if (clientRepository.existsByUser_PhoneNumber(addClientDTO.getPhoneNumber()))
            throw RestException.restThrow("ALREADY_REPORTED", HttpStatus.ALREADY_REPORTED);
        User user = userRepository.findByPhoneNumber(addClientDTO.getPhoneNumber())
                .orElseGet(() -> userRepository.save(new User(addClientDTO.getPhoneNumber())));

        Client client = new Client(user, addClientDTO);
        clientRepository.save(client);
        return ApiResult.successResponse(true);
    }

    @Override
    public ApiResult<Boolean> edit(ClientDTO clientDTO) {
        Optional<Client> optionalClient = clientRepository
                .findById(clientDTO.getId());
        if (optionalClient.isEmpty())
            throw RestException.restThrow("NOT_FOUND", HttpStatus.NOT_FOUND);
        Client client = optionalClient.get();
        clientDTO.setToClient(client);
        clientRepository.save(client);
        return ApiResult.successResponse(true);
    }

    @Override
    public ApiResult<Boolean> delete(UUID id) {
        if (!clientRepository.existsById(id))
            throw RestException.restThrow("NOT_FOUND",HttpStatus.NOT_FOUND);

        return ApiResult.successResponse(true);
    }


    @Override
    public ApiResult<List<ClientDTOFilter>> getAllClients(ViewDTO viewDTO, int page, int size) {

        StringBuilder query;

        query = new StringBuilder("WITH temp AS ( ");
        query.append("SELECT  CAST(c.id as varchar) id, ");
        query.append("c.name, ");
        query.append("u.phone_number, ");
        query.append("0 AS order_count, ");
        query.append("u.enabled ");
        query.append("FROM client c ");
        query.append("LEFT JOIN users u ON u.id = c.user_id ");

/**
 check admin is filtering sorting or searching
 */

        if (Objects.nonNull(viewDTO)){
            if (Objects.nonNull(viewDTO.getFiltering())
                    && Objects.nonNull(viewDTO.getFiltering().getFilterType()))
                query.append(filter(viewDTO.getFiltering()));

            query.append(") \n SELECT * FROM temp ");

            if (Objects.nonNull(viewDTO.getSearching())
                    && !viewDTO.getSearching().isBlank())
                query.append(search(viewDTO.getSearching()));

            if (Objects.nonNull(viewDTO.getSorting())
                    && !viewDTO.getSorting().isEmpty())
                query.append(sort(viewDTO.getSorting()));

        }

        query
                .append(" LIMIT ")
                .append(size)
                .append(" OFFSET ")
                .append(page * size);


        System.out.println(query);

        List<ClientDTOView> clientDTOViewList = clientRepository.getAllUsersByStringQuery(query.toString());

// get count of users order from order service
        Map<String, Integer> userOrderCounts = getUserOrdersCount(clientDTOViewList);

        if (Objects.isNull(userOrderCounts))
            throw RestException.restThrow("Order Service returned wrong response", HttpStatus.SERVICE_UNAVAILABLE);


        return ApiResult.successResponse(mapToClientDTO(userOrderCounts, clientDTOViewList));
    }


    private @NotNull List<ClientDTOFilter> mapToClientDTO(Map<String, Integer> userOrderCounts, List<ClientDTOView> clientDTOViewList){

        List<ClientDTOFilter> list = new ArrayList<>();

        for (ClientDTOView clientDTOView : clientDTOViewList) {
            list.add(
                    new ClientDTOFilter(
                            UUID.fromString(clientDTOView.getId()),
                            clientDTOView.getName(),
                            clientDTOView.getPhone_number(),
                            userOrderCounts.get(clientDTOView.getId()) == null ? 0 : userOrderCounts.get(clientDTOView.getId()),
                            clientDTOView.getEnabled()));
        }

        return list;
    }

    private Map<String, Integer> getUserOrdersCount(List<ClientDTOView> clientDTOViewList){

        Set<UUID> userIds = clientDTOViewList.stream().map(c -> UUID.fromString(c.getId())).collect(Collectors.toSet());

        return Objects.requireNonNull(Objects.requireNonNull(
                restTemplate.exchange(
                        ORDER_SERVICE_URL,
                        HttpMethod.POST,
                        new HttpEntity<>(userIds),
                        new ParameterizedTypeReference<ApiResult<Map<String, Integer>>>() {
                        })).getBody()).getData();
    }

    private String filter(FilterDTO filterDTO) {

        if (filterDTO.getFilterType() == FilterTypeEnum.ACTIVE)
            return " WHERE  u.enabled ";
        else
            return " WHERE u.enabled = false ";
    }

    private String search(String value) {

        StringBuilder query = new StringBuilder(" WHERE ");

        SearchingColumnsEnum[] columns = SearchingColumnsEnum.values();


        String column;

        for (int i = 0; i < columns.length; i++) {

            column = columns[i].name();

            query.append(column)
                    .append(" ILIKE '%")
                    .append(value)
                    .append("%'");

            if (i != columns.length-1)
                query.append(" OR ");
        }


        return query.toString();
    }

    private String sort(List<SortingDTO> sortingDTOs) {

        StringBuilder query = new StringBuilder(" ORDER BY ");

        SortingDTO sortingDTO;

        for (int i = 0; i < sortingDTOs.size(); i++) {

            sortingDTO = sortingDTOs.get(i);

            query.append(sortingDTO.getColumnName())
                    .append(" ")
                    .append(sortingDTO.getType());

            if (i != sortingDTOs.size()-1)
                query.append(", ");

        }

        return query.toString();
    }
}

