package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exception.ShopOperationException;

public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService shopService;
	
	@Test
	public void testGetShopList(){
    	Shop shopCondition = new Shop();
    	ShopCategory shopCategory = new ShopCategory();
    	shopCategory.setShopCategoryId(14L);
    	shopCondition.setShopCategory(shopCategory);
    	ShopExecution se= shopService.getShopList(shopCondition,1, 2);
    	System.out.println("店铺列表数为："+se.getShopList().size());
    	System.out.println("店铺总数为:"+se.getCount());
	}
	
	@Test
	@Ignore
	public void testModifyShop()throws ShopOperationException,FileNotFoundException{
		Shop shop =new Shop();
		shop.setShopId(1L);
		shop.setShopName("修改后的店铺");
		File shopImg=new File("E:\\test\\xiaoniao.png");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder=new ImageHolder("xiaoniao.png", is);
		ShopExecution shopExecution = shopService.modifyShop(shop, imageHolder );
		System.out.println("新的图片地址："+shopExecution.getShop().getShopImg());
	}
	@Test
	@Ignore
	public void testAddShop() throws FileNotFoundException {
		Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺66");
        shop.setShopDesc("test66");
        shop.setShopAddr("test66");
        shop.setPhone("test66");
        shop.setPriority(0);
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg=new File("E:\\test\\timg.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder imageHolder=new ImageHolder(shopImg.getName(), is);
        ShopExecution se = shopService.addShop(shop,imageHolder);
        assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
	}
	@Ignore
	@Test
	public void TestmodifyShopEnableStatus()throws ShopOperationException{
		Shop shop =new Shop();
		shop.setShopId(22L);
		shop.setEnableStatus(1);
		ShopExecution se=shopService.modifyShopEnableStatus(shop);
		assertEquals(ShopStateEnum.SUCCESS.getState(), se.getState());
	}
}
