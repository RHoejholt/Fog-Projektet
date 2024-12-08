package app.entities;

public class ProductVariant {

        private int productVariantId;
        private Product productId;
        private int length;

    public ProductVariant(int productVariantId, Product product, int length) {
        this.productVariantId = productVariantId;
        this.productId = product;
        this.length = length;
    }

    public int getProductVariantId() {
        return productVariantId;
    }

    public Product getProductId() {
        return productId;
    }

    public int getLength() {
        return length;
    }


}
