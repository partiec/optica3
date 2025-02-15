package ru.frolov.optica3.specification.products;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.frolov.optica3.dto.products.FrameDto;
import ru.frolov.optica3.entity.products.frame.FrameContainer;
import ru.frolov.optica3.enums.frames_enums.FrameInstallType;
import ru.frolov.optica3.enums.frames_enums.FrameMaterial;
import ru.frolov.optica3.enums.frames_enums.FrameUseType;

@Component
public class FrameSpec
        extends AbstractProductSpec {


    public Specification<FrameContainer> allContains(FrameDto filters) {

        return Specification.where
                (productCategoryLike(filters.getProductCategory())
                        .and(firmContains(filters.getFirm()))
                        .and(modelContains(filters.getModel()))
                        .and(detailsContains(filters.getDetails()))
                        .and(purchaseEqual(filters.getPurchase()))
                        .and(saleEqual(filters.getSale()))
                        /////////////////////////////////////
                        .and(frameUseTypeLike(filters.getFrameUseType()))
                        .and(frameInstallTypeLike(filters.getFrameInstallType()))
                        .and(frameMaterialLike(filters.getFrameMaterial()))
                );


    }


    public Specification<FrameContainer> allLike(FrameDto filters) {
        return Specification.where(
                productCategoryLike(filters.getProductCategory())
                        .and(firmLike(filters.getFirm()))
                        .and(modelLike(filters.getModel()))
                        .and(detailsLike(filters.getDetails()))
                        .and(purchaseEqual(filters.getPurchase()))
                        .and(saleEqual(filters.getSale()))
                        /////////////////////////////////////
                        .and(frameUseTypeLike(filters.getFrameUseType()))
                        .and(frameInstallTypeLike(filters.getFrameInstallType()))
                        .and(frameMaterialLike(filters.getFrameMaterial()))
        );
    }


    //========================================================================================================
    //========================================================================================================


    //-------------------------------
    // frame 3
    //-------------------------------
    public Specification<FrameContainer> frameUseTypeLike(FrameUseType filter) {
        System.out.println("принят FrameUseType = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("frameUseTypeLike(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("frameUseTypeLike(" + filter + "/" + filter.getStr() + ") - FIND");
            return criteriaBuilder.like(
                    root.get("frameUseType"),
                    filter.toString());
        };
    }

    public Specification<FrameContainer> frameInstallTypeLike(FrameInstallType filter) {
        System.out.println("принят FrameInstallType = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("frameInstallTypeLike(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("frameInstallTypeLike(" + filter + "/" + filter.getStr() + ") - FIND");
            return criteriaBuilder.like(
                    root.get("frameInstallType"),
                    filter.toString());
        };
    }

    public Specification<FrameContainer> frameMaterialLike(FrameMaterial filter) {
        System.out.println("принят FrameMaterial = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("_____materialLike(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("frameMaterialLike(" + filter + "/" + filter.getStr() + ") - FIND");
            return criteriaBuilder.like(
                    root.get("frameMaterial"),
                    filter.toString());
        };
    }


}
