package com.erp.api.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.api.dao.WalletDao;
import com.erp.api.dto.FetchWalletTrDto;
import com.erp.api.dto.PaymentDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.exceptions.ValidationException;
import com.erp.api.service.WalletService;


@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletDao walletDao;


//    @Override
//    public ResponseDto getSOA(String token,String fromDate,String toDate,int pageCount,int pageNumber,boolean search,String columnName,String columnValue) {
//       return walletDao.getSOA(token,fromDate,toDate,pageCount,pageNumber,search,columnName,columnValue,new ResponseDto());
//    }

    @Override
    public ResponseDto addCreditRequest(PaymentDto paymentDto, String token) {
        if(paymentDto.getAmount()>0)
        {
            return walletDao.addCreditRequest(token,paymentDto, new ResponseDto());
        }
        else
        {
            throw new ValidationException("301","Please enter valid amount",null);
        }

    }


@Override
public ResponseDto getSOA(String token, FetchWalletTrDto soaObj) {
	// TODO Auto-generated method stub
	return walletDao.getSOA(token,soaObj,new ResponseDto());
}

//	@Override
//	public Object getSOA(String token, FetchWalletTrDto soaObj) {
//	       return walletDao.getSOA(token,soaObj,new ResponseDto());
//	}
}
