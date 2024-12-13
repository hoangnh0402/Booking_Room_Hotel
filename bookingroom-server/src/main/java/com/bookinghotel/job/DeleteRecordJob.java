package com.bookinghotel.job;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableAsync
@Configuration
@EnableScheduling
@ConditionalOnExpression("true")
@RequiredArgsConstructor
public class DeleteRecordJob {

  private final PostService postService;

  private final RoomService roomService;

  private final MediaService mediaService;

  private final SaleService saleService;

  private final HotelService hotelService;

  private final ProductService productService;

  private final VerificationTokenService verificationTokenService;

  /**
   * This job starts at 3:00 AM everyday
   */
  @Scheduled(cron = "0 0 3 * * *")
  void deleteAllRecordStepOne() {
    roomService.deleteRoomByDeleteFlag(CommonConstant.TRUE, CommonConstant.DAYS_TO_DELETE_RECORDS);
    saleService.deleteSaleByDeleteFlag(CommonConstant.TRUE, CommonConstant.DAYS_TO_DELETE_RECORDS);
  }

  /**
   * This job starts at 3:30 AM everyday
   */
  @Scheduled(cron = "0 30 3 * * *")
  void deleteAllRecordStepTwo() {
    postService.deletePostByDeleteFlag(CommonConstant.TRUE, CommonConstant.DAYS_TO_DELETE_RECORDS);
    hotelService.deleteServiceByDeleteFlag(CommonConstant.TRUE, CommonConstant.DAYS_TO_DELETE_RECORDS);
  }

  /**
   * This job starts at 4:00 AM everyday
   */
  @Scheduled(cron = "0 0 4 * * *")
  void deleteAllRecordStepThree() {
    mediaService.deleteMediaByDeleteFlag(CommonConstant.TRUE, CommonConstant.DAYS_TO_DELETE_RECORDS);
    productService.deleteProductByDeleteFlag(CommonConstant.TRUE, CommonConstant.DAYS_TO_DELETE_RECORDS);
  }

  /**
   * This job starts at 4:30 AM everyday
   */
  @Scheduled(cron = "0 30 4 * * *")
  void deleteAllRecordJunkVerificationToken() {
    verificationTokenService.deleteAllJunkVerificationToken();;
  }

}
