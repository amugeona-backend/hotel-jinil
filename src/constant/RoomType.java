package constant;

public enum RoomType {
    STANDARD("standard room"),
    SUPERIOR("superior room"),
    DELUXE("deluxe room"),
    SUITE("suite room");

    final String type;

    RoomType(String type) {
        this.type = type;
    }
}
