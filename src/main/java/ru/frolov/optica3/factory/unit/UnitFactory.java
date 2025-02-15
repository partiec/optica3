package ru.frolov.optica3.factory.unit;

import org.springframework.stereotype.Component;
import ru.frolov.optica3.dto._order.OrderAndClientDto;
import ru.frolov.optica3.dto.products.*;
import ru.frolov.optica3.entity.AbstractContainer;
import ru.frolov.optica3.entity.AbstractUnit;
import ru.frolov.optica3.entity.client.Client;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.entity.products.accessory.AccessoryUnit;
import ru.frolov.optica3.entity.products.contact.ContactContainer;
import ru.frolov.optica3.entity.products.contact.ContactUnit;
import ru.frolov.optica3.entity.products.frame.FrameContainer;
import ru.frolov.optica3.entity.products.frame.FrameUnit;
import ru.frolov.optica3.entity.products.glass.GlassContainer;
import ru.frolov.optica3.entity.products.glass.GlassUnit;
import ru.frolov.optica3.enums.abstract_enums.ProductCategory;
import ru.frolov.optica3.enums.abstract_enums.ProductStatus;
import ru.frolov.optica3.enums.accessory_enums.AccessoryCategory;
import ru.frolov.optica3.enums.contact_enums.*;
import ru.frolov.optica3.enums.frames_enums.FrameInstallType;
import ru.frolov.optica3.enums.frames_enums.FrameMaterial;
import ru.frolov.optica3.enums.frames_enums.FrameUseType;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;
import ru.frolov.optica3.enums.order_enums.OrderPayment;
import ru.frolov.optica3.enums.order_enums.OrderStage;

@Component
public class UnitFactory {

    // unit
    public static AbstractUnit createUnitInstance(AbstractProductDto dto) {
        AbstractUnit instance = null;

        // accessory
        if (dto.getClass().getSimpleName().equals("AccessoryDto")) {

            AccessoryUnit u = new AccessoryUnit();
            AccessoryDto d = (AccessoryDto) dto;

            u.setProductCategory(ProductCategory.ACCESSORY);
            u.setProductStatus(ProductStatus.STOCK);            // !
            u.setFirm(d.getFirm());
            u.setModel(d.getModel());
            u.setDetails(d.getDetails());
            u.setPurchase(d.getPurchase());
            u.setSale(d.getSale());
            ////////////////////////////////////////////
            u.setAccessoryCategory(
                    d.getAccessoryCategory() == null ? AccessoryCategory.NOT_SELECTED : d.getAccessoryCategory());
            ////////////////////////////////////////////

            instance = u;
        }

        // contact
        if (dto.getClass().getSimpleName().equals("ContactDto")) {

            ContactUnit u = new ContactUnit();
            ContactDto d = (ContactDto) dto;

            // копировать поля
            u.setProductCategory(d.productCategory);
            u.setProductStatus(ProductStatus.STOCK);        // !
            u.setFirm(d.getFirm());
            u.setModel(d.getModel());
            u.setDetails(d.getDetails());
            u.setPurchase(d.getPurchase());
            u.setSale(d.getSale());
            ////////////////////////////////////////////////////////////////////////
            u.setContactDesign(
                    d.getContactDesign() == null ? ContactDesign.NOT_SELECTED : d.getContactDesign());
            u.setContactPeriod(
                    d.getContactPeriod() == null ? ContactPeriod.NOT_SELECTED : d.getContactPeriod());
            u.setContactOxygen(
                    d.getContactOxygen() == null ? ContactOxygen.NOT_SELECTED : d.getContactOxygen());
            u.setContactWater(
                    d.getContactWater() == null ? ContactWater.NOT_SELECTED : d.getContactWater());
            u.setContactDiameter(
                    d.getContactDiameter() == null ? ContactDiameter.NOT_SELECTED : d.getContactDiameter());
            u.setContactRadius(
                    d.getContactRadius() == null ? ContactRadius.NOT_SELECTED : d.getContactRadius());
            u.setDioptre(
                    d.getDioptre() == null ? "_" : d.getDioptre());
            /////////////////////////////////////////////////////////////////////////

            instance = u;
        }
        // frame
        if (dto.getClass().getSimpleName().equals("FrameDto")) {

            FrameUnit u = new FrameUnit();
            FrameDto d = (FrameDto) dto;

            // копировать поля
            u.setProductCategory(ProductCategory.FRAME);
            u.setProductStatus(ProductStatus.STOCK);        // !
            u.setFirm(d.getFirm());
            u.setModel(d.getModel());
            u.setDetails(d.getDetails());
            u.setPurchase(d.getPurchase());
            u.setSale(d.getSale());
            ///////////////////////////////////////////
            u.setFrameUseType(
                    d.getFrameUseType() == null ? FrameUseType.NOT_SELECTED : d.getFrameUseType());
            u.setFrameInstallType(
                    d.getFrameInstallType() == null ? FrameInstallType.NOT_SELECTED : d.getFrameInstallType());
            u.setFrameMaterial(
                    d.getFrameMaterial() == null ? FrameMaterial.NOT_SELECTED : d.getFrameMaterial());
            ///////////////////////////////////////////

            instance = u;
        }
        // glass
        if (dto.getClass().getSimpleName().equals("GlassDto")) {

            GlassUnit u = new GlassUnit();
            GlassDto d = (GlassDto) dto;

            // копировать поля
            u.setProductCategory(ProductCategory.GLASS);
            u.setProductStatus(ProductStatus.STOCK);        // !
            u.setFirm(d.getFirm());
            u.setModel(d.getModel());
            u.setDetails(d.getDetails());
            u.setPurchase(d.getPurchase());
            u.setSale(d.getSale());
            ///////////////////////////////////////////
            u.setGlassMaterial(
                    d.getGlassMaterial() == null ? GlassMaterial.NOT_SELECTED : d.getGlassMaterial());
            u.setGlassDesign(
                    d.getGlassDesign() == null ? GlassDesign.NOT_SELECTED : d.getGlassDesign());
            u.setGlassCoat(
                    d.getGlassCoat() == null ? GlassCoat.NOT_SELECTED : d.getGlassCoat());
            u.setDioptre(
                    d.getDioptre() == null ? "_" : d.getDioptre());
            ///////////////////////////////////////////

            instance = u;
        }
        return instance;
    }


}
