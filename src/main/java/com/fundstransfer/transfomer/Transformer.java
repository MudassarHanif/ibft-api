package com.fundstransfer.transfomer;

import com.fundstransfer.dto.FundsTransferRequestDto;
import com.fundstransfer.domain.model.FundsTransferModel;
import com.fundstransfer.domain.model.LedgerModel;
import com.fundstransfer.dto.LedgerEntryDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class Transformer {

  protected final ModelMapper modelMapper = new ModelMapper();

  public Transformer() {
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    modelMapper.createTypeMap(FundsTransferRequestDto.class, FundsTransferModel.class)
        .addMapping(FundsTransferRequestDto::getSenderAccountId, FundsTransferModel::setSenderAccountNumber)
        .addMapping(FundsTransferRequestDto::getReceiverAccountId, FundsTransferModel::setReceiverAccountNumber);
    modelMapper.createTypeMap(LedgerModel.class, LedgerEntryDto.class)
        .addMapping(LedgerModel::getAccountNumber, LedgerEntryDto::setAccountNumber);

  }

  public <S, D> D transform(S sourceClass, Class<D> destClass) {
    return modelMapper.map(sourceClass, destClass);
  }

}