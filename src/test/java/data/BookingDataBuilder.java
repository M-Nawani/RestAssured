package data;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class BookingDataBuilder {

    public BookingData bookindatabuilder(){
        Faker faker = new Faker();
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
        return BookingData.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .totalprice(faker.number().numberBetween(1,2000))
                .depositpaid(true)
                .additionalneeds("breakfast")
                .bookingdates(BookingDates.builder()
                        .checkin(formatter.format(faker.date().past(20, TimeUnit.DAYS)))
                        .checkout(formatter.format(faker.date().future(5, TimeUnit.DAYS))).build())
                .build();

    }

    public PartialBookingData partialdatabuilder(){
        Faker faker = new Faker();
        return PartialBookingData.builder()
                .firstname(faker.name().firstName())
                .totalprice(faker.number().numberBetween(1,5000))
                .build();
    }
}
