package xyz.ferus.tempus.api.service;

import javax.annotation.Nonnull;

public enum StoragePreference {

    MARIADB("mariadb"),
    MYSQL("mysql"),
    SQLITE("sqlite"),
    ;

    private final String id;

    StoragePreference(final String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "StoragePreference[" + this.id.toUpperCase() + "]";
    }

    public static StoragePreference of(@Nonnull final String id) {
        for (final StoragePreference preference : values()) {
            if (preference.getId().equalsIgnoreCase(id)) {
                return preference;
            }
        }

        return SQLITE;
    }
}
