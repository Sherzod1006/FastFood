package uz.pdp.apporder.payload;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;


@Getter
@NoArgsConstructor
@Setter
public class OrderProductDTO {

    @NotNull
    private Integer productId;

    @NotNull
    private Short quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductDTO that = (OrderProductDTO) o;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        if (Objects.isNull(productId))
            return 0;
        return productId.hashCode();
    }
}
