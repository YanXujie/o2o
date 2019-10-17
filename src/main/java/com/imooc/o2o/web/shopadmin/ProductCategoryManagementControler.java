package com.imooc.o2o.web.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductCategoryStateEnum;
import com.imooc.o2o.exception.ProductCategoryOperationException;
import com.imooc.o2o.service.impl.ProductCategoryServiceImpl;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementControler {
	@Autowired
	private ProductCategoryServiceImpl productCategoryServiceImpl;
	@RequestMapping(value="/getproductcategorylist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getProductCategoryList(HttpServletRequest request){
		Map<String,Object>modelMap = new HashMap<String,Object>();
		Shop currentShop=(Shop)request.getSession().getAttribute("currentShop");
		List<ProductCategory> list = null;
		if(currentShop!=null&&currentShop.getShopId()>0) {
			list=productCategoryServiceImpl.getProductCategoryList(currentShop.getShopId());
			modelMap.put("data", list);
			modelMap.put("success", true);
			return modelMap;
		}else {
			modelMap.put("false", null);
			return modelMap;
		}
	}
	
	@RequestMapping(value="/removeproductcategory",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> removeProductCategory(Long productCategoryId,HttpServletRequest request){
		Map<String,Object>modelMap = new HashMap<String,Object>();
		if(productCategoryId>0&&productCategoryId!=null) {
			try {
				Shop currentShop=(Shop)request.getSession().getAttribute("currentShop");
				ProductCategoryExecution pe = productCategoryServiceImpl.deleteProductCategory(productCategoryId, currentShop.getShopId());
				if(pe.getState()==ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch (ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少选择一个商品类别");
		}
		return modelMap;
	}
	
	
	@RequestMapping(value="/addproductcategorys",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> addProductCategorys(@RequestBody List<ProductCategory>productCategoryList,HttpServletRequest request){
		Map<String,Object>modelMap = new HashMap<String,Object>();
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		for(ProductCategory pc:productCategoryList) {
			pc.setShopId(currentShop.getShopId());
		}
		if(productCategoryList!=null&&productCategoryList.size()>0) {
			try {
				ProductCategoryExecution pe = productCategoryServiceImpl.batchAddProductCategory(productCategoryList);
				if(pe.getState()==ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("effectNum", pe.getProductCategoryList().size());
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少输入一个商品类别");
		}
		return modelMap;
	}
}