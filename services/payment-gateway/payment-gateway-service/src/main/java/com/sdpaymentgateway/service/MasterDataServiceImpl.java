package com.sdpaymentgateway.service;

import com.sdpaymentgateway.exception.ErrorConstant;
import com.sdpaymentgateway.exception.ErrorConstants;

public class MasterDataServiceImpl implements MasterDataService {

	@Override
	public ErrorConstants LoadErrorCodes() {
		// TODO Auto-generated method stub
		ErrorConstants errorCodes = new ErrorConstants();
		errorCodes.add(new ErrorConstant("401","INVALID_REQUEST_INFO"));
		errorCodes.add(new ErrorConstant("402","INVALID_REQUEST"));
		errorCodes.add(new ErrorConstant("403","INVALID_REQUEST"));
		errorCodes.add(new ErrorConstant("404","INVALID_REQUEST"));
		errorCodes.add(new ErrorConstant("405","INVALID_REQUEST"));
		errorCodes.add(new ErrorConstant("406","INVALID_REQUEST"));
		
		return errorCodes;
	}

}
