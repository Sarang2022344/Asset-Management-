export class AssetAllocation {
    constructor(data) {
        this.allocationId = data.allocationId;
        this.asset = data.asset;
        this.employee = data.employee;
        this.allocatedDate = data.allocatedDate;
        this.returnedDate = data.returnedDate;
        this.status = data.status;
        this.performedBy = data.performedBy;
    }
}