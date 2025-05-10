/**
 * @typedef {Object} Category
 * @property {number} categoryId
 * @property {string} categoryName
 */

/**
 * @typedef {Object} HardwareDetails
 * @property {string} serialNumber
 * @property {string} specifications
 * @property {string} brand
 * @property {string} type
 */

/**
 * @typedef {Object} SoftwareDetails
 * @property {string[]} licenses
 * @property {string} licenseExpiryDate
 * @property {string} version
 * @property {string} supportedOS
 */

/**
 * @typedef {Object} Asset
 * @property {number} [assetId]
 * @property {string} name
 * @property {Category} category
 * @property {string[]} [image]
 * @property {string} [barcode]
 * @property {string} [purchasedDate]
 * @property {string} [vendor]
 * @property {string} [invoicePath]
 * @property {number} [price]
 * @property {string} [status]
 * @property {string} [warrantyStartDate]
 * @property {string} [warrantyRenewalDate]
 * @property {HardwareDetails} [hardwareDetails]
 * @property {SoftwareDetails} [softwareDetails]
 */

/** @type {Asset} */
const newAsset = {
    name: "Laptop",
    category: { categoryId: 1, categoryName: "Hardware" },
    vendor: "Dell",
    price: 1200,
  };
  
  export default newAsset;
  