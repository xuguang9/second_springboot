package xg.inclass.second_springboot.jdk8;

import org.junit.Before;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

/**
 * T00010
 */
public class DateApi {


    public Clock clock = null;

    @Before
    public void init() {
        clock = Clock.systemDefaultZone();
    }


    @Test
    public void testClock() {
        long millis = clock.millis(); //取毫秒数
//      System.out.println(millis);
        Instant instant = clock.instant();//转换为date
        Date date = Date.from(instant);
//      System.out.println(date);

//        System.out.println(ZoneId.getAvailableZoneIds());// prints all available timezone ids

        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
//        System.out.println(zone1.getRules());// ZoneRules[currentStandardOffset=+01:00]
//        System.out.println(zone2.getRules());// ZoneRules[currentStandardOffset=-03:00]

        LocalTime time1 = LocalTime.now(zone1);
        LocalTime time2 = LocalTime.now(zone2);
//        System.out.println(time1); //10:54:20.644
//        System.out.println(time2); //05:54:20.650
//        System.out.println(time1.isBefore(time2)); //false
        long minus = ChronoUnit.MINUTES.between(time1, time2); //time2-time1 -299
        long hours = ChronoUnit.HOURS.between(time1, time2);
//        System.out.println(minus);
//        System.out.println(hours);
        LocalTime late = LocalTime.of(12, 12, 12);
//        System.out.println(late);
        DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        LocalTime parse = LocalTime.parse("13:12:12");
//        System.out.println(parse.toString());

        //localdate
        LocalDate localDate = LocalDate.parse("2015-12-12");
//        System.out.println(localDate);
        LocalDate now = LocalDate.now();
        LocalDate tom = now.plus(1L, ChronoUnit.DAYS);
        LocalDate yes = tom.minusDays(2);

        //localdatetime
        LocalDateTime dateTime = LocalDateTime.of(2011, 12, 12, 23, 12, 34);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse1 = LocalDateTime.parse("2017-12-18T12:22:22",
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String  now1 = LocalDateTime.now().format(formatter);
        LocalDateTime now2 = LocalDateTime.now();
        System.out.println("now1="+now1);
        System.out.println("now2="+now2.format(formatter));
       // long between = ChronoUnit.MINUTES.between(dateTime, now1);
//        System.out.println(between);
        System.out.println("parse1="+parse1.toString().replace("T"," "));
        /*总结下,就两个功能,
        * 1:通过int,string,转换为datetime,  方法of,parse
        * 2:转换为datetime后, 用其方法可以计算差值或在本值基础上增/删
        * */



    }


}
