package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.enums.LocalAuthStateEnum;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthServiceTest extends BaseTest {

	@Autowired
	LocalAuthService localAuthService;
	
	@Test
	public void TestARegister() {
		LocalAuth localAuth =new LocalAuth();
		PersonInfo personInfo = new PersonInfo();
		localAuth.setUsername("localtestusername7");
		localAuth.setpassword("123456");
		personInfo.setName("localtestname7");
		personInfo.setUserType(1);
		localAuth.setPersonInfo(personInfo);
		LocalAuthExecution le =localAuthService.register(localAuth);
		assertEquals(le.getState(), LocalAuthStateEnum.SUCCESS.getState());
	}
	@Ignore
	@Test
	public void TestBGetLocalInfoByUserName(){
		LocalAuth localAuth = localAuthService.getLocalInfoByUserName("localtestusername2");
		assertEquals(localAuth.getpassword(), "123456");
	}
	@Test
	@Ignore
	public void TestCModifiPassword(){
		String username="shopadmin";
		String oldPassword="123456";
		String newPassword="admin";
		LocalAuthExecution le=localAuthService.modifiPassword(username, oldPassword, newPassword);
		assertEquals(le.getState(), LocalAuthStateEnum.SUCCESS.getState());
	}
	@Test
	@Ignore
	public void TestDResetPassword(){
		String username="shopadmin";
		String email="test";
		String newPassword="123456";
		LocalAuthExecution le =localAuthService.resetPassword(username, email, newPassword);
		assertEquals(le.getState(), LocalAuthStateEnum.SUCCESS.getState());
	}
}
