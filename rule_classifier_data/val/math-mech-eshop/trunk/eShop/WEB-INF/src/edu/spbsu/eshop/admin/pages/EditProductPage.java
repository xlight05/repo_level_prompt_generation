package edu.spbsu.eshop.admin.pages;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import edu.spbsu.eshop.beans.ErrorBean;
import edu.spbsu.eshop.storage.Storage;
import edu.spbsu.eshop.storage.data.Category;
import edu.spbsu.eshop.storage.data.Group;
import edu.spbsu.eshop.storage.data.Product;

public class EditProductPage {
    private Product currentProduct;
    private UploadedFile photo;
    private boolean justAdded = false;
    private final AdminRoomPage addProductItem;
    private final AdminRoomPage editProductItem;

    public EditProductPage() {

	OnPageRequestAction addAction = new AbstractOnPageRequestAction() {

	    @Override
	    protected Object getObjectById(Long id) {
		return Storage.getCategory(id);
	    }

	    @Override
	    protected void setObject(Object object) {
		justAdded = true;
		currentProduct = new Product();
		currentProduct.setCategory((Category) object);
	    }

	};
	addProductItem = new AbstractAdminRoomPage("add_product", null,
		"editProduct.jsf", addAction, Group.Permission.AddProduct);

	OnPageRequestAction editAction = new AbstractOnPageRequestAction() {

	    @Override
	    protected Object getObjectById(Long id) {
		return Storage.getProduct(id);
	    }

	    @Override
	    protected void setObject(Object object) {
		justAdded = false;
		currentProduct = (Product) object;
	    }

	};
	editProductItem = new AbstractAdminRoomPage("edit_product", null,
		"editProduct.jsf", editAction, Group.Permission.EditProduct);

    }

    public Product getCurrentProduct() {
	return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
	this.currentProduct = currentProduct;
    }

    public UploadedFile getPhoto() {
	return photo;
    }

    public void setPhoto(UploadedFile photo) {
	this.photo = photo;
    }

    public AdminRoomPage getAddProductItem() {
	return addProductItem;
    }

    public AdminRoomPage getEditProductItem() {
	return editProductItem;
    }

    public String save() {
	ErrorBean.setErrorMessage("just check");
	try {
	    if (photo != null) {
		String oldPhoto = currentProduct.getPhotoFileName();
		if (oldPhoto != null && !oldPhoto.equals("")) {
		    Storage.removePhoto(oldPhoto);
		}
		currentProduct.setPhotoFileName(Storage.saveImage(photo));
	    }
	    if (justAdded) {
		Storage.persist(currentProduct);
		return "";
	    }

	    Storage.merge(currentProduct);
	    return "error";
	} catch (Throwable e) {
	    ErrorBean.setErrorMessage(e.getMessage());
	    return "error";
	}
    }

}
