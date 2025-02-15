package ru.frolov.optica3.factory.container;

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
public class ContainerFactory {


    public static AbstractContainer createContainerInstance(AbstractProductDto dto) {
        AbstractContainer instance = null;
        // accessory
        if (dto.getClass().getSimpleName().equals("AccessoryDto")) {

            AccessoryContainer c = new AccessoryContainer();
            AccessoryDto d = (AccessoryDto) dto;

            c.setProductCategory(ProductCategory.ACCESSORY);
            c.setFirm(d.getFirm());
            c.setModel(d.getModel());
            c.setDetails(d.getDetails());
            c.setPurchase(d.getPurchase());
            c.setSale(d.getSale());
            ////////////////////////////////////////////
            c.setAccessoryCategory(
                    d.getAccessoryCategory() == null ? AccessoryCategory.NOT_SELECTED : d.getAccessoryCategory());
            ////////////////////////////////////////////

            instance = c;
        }
        // contact
        if (dto.getClass().getSimpleName().equals("ContactDto")) {

            ContactContainer c = new ContactContainer();
            ContactDto d = (ContactDto) dto;

            // копировать поля
            c.setProductCategory(d.productCategory);
            c.setFirm(d.getFirm());
            c.setModel(d.getModel());
            c.setDetails(d.getDetails());
            c.setPurchase(d.getPurchase());
            c.setSale(d.getSale());
            ////////////////////////////////////////////////////////////////////////
            c.setContactDesign(
                    d.getContactDesign() == null ? ContactDesign.NOT_SELECTED : d.getContactDesign());
            c.setContactPeriod(
                    d.getContactPeriod() == null ? ContactPeriod.NOT_SELECTED : d.getContactPeriod());
            c.setContactOxygen(
                    d.getContactOxygen() == null ? ContactOxygen.NOT_SELECTED : d.getContactOxygen());
            c.setContactWater(
                    d.getContactWater() == null ? ContactWater.NOT_SELECTED : d.getContactWater());
            c.setContactDiameter(
                    d.getContactDiameter() == null ? ContactDiameter.NOT_SELECTED : d.getContactDiameter());
            c.setContactRadius(
                    d.getContactRadius() == null ? ContactRadius.NOT_SELECTED : d.getContactRadius());
            c.setDioptre(
                    d.getDioptre() == null ? "_" : d.getDioptre());
            /////////////////////////////////////////////////////////////////////////

            instance = c;
        }
        // frame
        if (dto.getClass().getSimpleName().equals("FrameDto")) {

            FrameContainer c = new FrameContainer();
            FrameDto d = (FrameDto) dto;

            // копировать поля
            c.setProductCategory(ProductCategory.FRAME);
            c.setFirm(d.getFirm());
            c.setModel(d.getModel());
            c.setDetails(d.getDetails());
            c.setPurchase(d.getPurchase());
            c.setSale(d.getSale());
            ///////////////////////////////////////////
            c.setFrameUseType(
                    d.getFrameUseType() == null ? FrameUseType.NOT_SELECTED : d.getFrameUseType());
            c.setFrameInstallType(
                    d.getFrameInstallType() == null ? FrameInstallType.NOT_SELECTED : d.getFrameInstallType());
            c.setFrameMaterial(
                    d.getFrameMaterial() == null ? FrameMaterial.NOT_SELECTED : d.getFrameMaterial());
            ///////////////////////////////////////////

            instance = c;
        }
        // glass
        if (dto.getClass().getSimpleName().equals("GlassDto")) {

            GlassContainer c = new GlassContainer();
            GlassDto d = (GlassDto) dto;

            // копировать поля
            c.setProductCategory(ProductCategory.GLASS);
            c.setFirm(d.getFirm());
            c.setModel(d.getModel());
            c.setDetails(d.getDetails());
            c.setPurchase(d.getPurchase());
            c.setSale(d.getSale());
            ///////////////////////////////////////////
            c.setGlassMaterial(
                    d.getGlassMaterial() == null ? GlassMaterial.NOT_SELECTED : d.getGlassMaterial());
            c.setGlassDesign(
                    d.getGlassDesign() == null ? GlassDesign.NOT_SELECTED : d.getGlassDesign());
            c.setGlassCoat(
                    d.getGlassCoat() == null ? GlassCoat.NOT_SELECTED : d.getGlassCoat());
            c.setDioptre(
                    d.getDioptre() == null ? "_" : d.getDioptre());
            ///////////////////////////////////////////

            instance = c;
        }
        return instance;
    }



}
