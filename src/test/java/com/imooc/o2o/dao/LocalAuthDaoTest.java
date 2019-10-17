package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.PersonInfo;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class LocalAuthDaoTest extends BaseTest {
	@Autowired
	LocalAuthDao localAuthDao;
	
	
	@Test
	@Ignore
	public void TestAInsertLocalAuth() {
		LocalAuth localAuth = new LocalAuth();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1L);
		localAuth.setPersonInfo(personInfo);
		localAuth.setUsername("test2");
		localAuth.setpassword("123456");
		localAuth.setCreateTime(new Date());
		int effectedNum = localAuthDao.insertLocalAuth(localAuth);
		assertEquals(effectedNum, 1);
	}
	@Test
	@Ignore
	public void TestBqueryLocalInfoByUserName() {
		
		LocalAuth localAuth1 = localAuthDao.queryLocalInfoByUserName("test123456");
		assertEquals(localAuth1, null);
	}
	@Test
	@Ignore
	public void TestCcountLocalInfoByUserName() {
		
		int effectedNum = localAuthDao.countLocalInfoByUserName("shopadmin2");
		assertEquals(effectedNum, 0);
	}
	@Test
	public void TestDupdatePassword() {
		int effectedNum = localAuthDao.updatePassword("superadmin", "123456");
		assertEquals(effectedNum,1);
	}
}
