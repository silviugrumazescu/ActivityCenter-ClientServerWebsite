export default class Activity {
    constructor(id, name, description, type, phoneNumber, address, locationX, locationY, imageUrls) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.locationX = locationX;
        this.locationY = locationY;
        this.imageUrls = imageUrls;
    }
}