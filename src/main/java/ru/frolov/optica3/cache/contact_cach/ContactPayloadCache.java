package ru.frolov.optica3.cache.contact_cach;

import lombok.experimental.UtilityClass;
import ru.frolov.optica3.payload.contact_payloads.ContactPayload;

@UtilityClass
public class ContactPayloadCache {

    private ContactPayload contactPayload;

    public static ContactPayload getContactPayload() {
        return contactPayload;
    }

    public static void setContactPayload(ContactPayload contactPayload) {
        ContactPayloadCache.contactPayload = contactPayload;
    }
}
