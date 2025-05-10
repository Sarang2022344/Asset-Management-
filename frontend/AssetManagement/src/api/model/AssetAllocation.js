export class AssetAllocation {
    constructor(data) {
        this.allocationId = data.allocationId;
        this.asset = data.asset;
        this.barcode = data.asset?.barcode || ""; // Ensure barcode is included
        this.employee = data.employee;
        this.allocatedDate = data.allocatedDate;
        this.returnedDate = data.returnedDate;
        this.status = data.status;
        this.performedBy = data.performedBy;
    }
}
