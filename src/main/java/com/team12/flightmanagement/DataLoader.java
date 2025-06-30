package com.team12.flightmanagement;

import com.team12.flightmanagement.entity.*;
import com.team12.flightmanagement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private AircraftRepository aircraftRepository;

    @Override
    public void run(String... args) throws Exception {
        // delete existing data ahead of restart
        aircraftRepository.deleteAll();
        passengerRepository.deleteAll();
        airportRepository.deleteAll();
        cityRepository.deleteAll();

        // Createcity
        City stJohns = new City(); stJohns.setName("St. John's"); stJohns.setProvince("NL"); stJohns.setPopulation(110000);
        City halifax = new City(); halifax.setName("Halifax"); halifax.setProvince("NS"); halifax.setPopulation(430000);
        City montreal = new City(); montreal.setName("Montreal"); montreal.setProvince("QC"); montreal.setPopulation(1780000);
        City ottawa = new City(); ottawa.setName("Ottawa"); ottawa.setProvince("ON"); ottawa.setPopulation(1010000);
        City medicineHat = new City(); medicineHat.setName("Medicine Hat"); medicineHat.setProvince("AB"); medicineHat.setPopulation(65000);
        City yellowknife = new City(); yellowknife.setName("Yellowknife"); yellowknife.setProvince("NT"); yellowknife.setPopulation(21000);

        cityRepository.saveAll(Arrays.asList(stJohns, halifax, montreal, ottawa, medicineHat, yellowknife));

        // Create airport
        Airport yyt = new Airport(); yyt.setName("St. John's International Airport"); yyt.setCode("YYT"); yyt.setCity(stJohns);
        Airport yhz = new Airport(); yhz.setName("Halifax Stanfield Intl Airport"); yhz.setCode("YHZ"); yhz.setCity(halifax);
        Airport yul = new Airport(); yul.setName("Montréal–Trudeau Intl Airport"); yul.setCode("YUL"); yul.setCity(montreal);
        Airport yow = new Airport(); yow.setName("Ottawa Macdonald–Cartier Intl"); yow.setCode("YOW"); yow.setCity(ottawa);
        Airport yxh = new Airport(); yxh.setName("Medicine Hat Regional Airport"); yxh.setCode("YXH"); yxh.setCity(medicineHat);
        Airport yzf = new Airport(); yzf.setName("Yellowknife Airport"); yzf.setCode("YZF"); yzf.setCity(yellowknife);

        airportRepository.saveAll(Arrays.asList(yyt, yhz, yul, yow, yxh, yzf));

        // Attach airports and city
        stJohns.setAirports(Collections.singletonList(yyt));
        halifax.setAirports(Collections.singletonList(yhz));
        montreal.setAirports(Collections.singletonList(yul));
        ottawa.setAirports(Collections.singletonList(yow));
        medicineHat.setAirports(Collections.singletonList(yxh));
        yellowknife.setAirports(Collections.singletonList(yzf));
        cityRepository.saveAll(Arrays.asList(stJohns, halifax, montreal, ottawa, medicineHat, yellowknife));

        // Create passengers
        Passenger p1 = new Passenger(); p1.setFirstName("Richard"); p1.setLastName("Wilton"); p1.setPhoneNumber("613-235-1312"); p1.setCity(montreal);
        Passenger p2 = new Passenger(); p2.setFirstName("Lucas"); p2.setLastName("Hoyles"); p2.setPhoneNumber("782-414-7111"); p2.setCity(stJohns);
        Passenger p3 = new Passenger(); p3.setFirstName("Muhammad"); p3.setLastName("Mumtaz"); p3.setPhoneNumber("942-660-8921"); p3.setCity(ottawa);
        Passenger p4 = new Passenger(); p4.setFirstName("Abigail"); p4.setLastName("Scherger"); p4.setPhoneNumber("306-330-9340"); p4.setCity(yellowknife);
        Passenger p5 = new Passenger(); p5.setFirstName("Steve"); p5.setLastName("Morrison"); p5.setPhoneNumber("709-424-3715"); p5.setCity(stJohns);
        Passenger p6 = new Passenger(); p6.setFirstName("Chris"); p6.setLastName("Morrison"); p6.setPhoneNumber("709-725-6520"); p6.setCity(stJohns);
        Passenger p7 = new Passenger(); p7.setFirstName("Alex"); p7.setLastName("Brazil"); p7.setPhoneNumber("709-424-8155"); p7.setCity(halifax);
        Passenger p8 = new Passenger(); p8.setFirstName("Ben"); p8.setLastName("Hodder"); p8.setPhoneNumber("604-563-7856"); p8.setCity(medicineHat);

        // Initialize
        p1.setAircraftList(new ArrayList<>());
        p2.setAircraftList(new ArrayList<>());
        p3.setAircraftList(new ArrayList<>());
        p4.setAircraftList(new ArrayList<>());
        p5.setAircraftList(new ArrayList<>());
        p6.setAircraftList(new ArrayList<>());
        p7.setAircraftList(new ArrayList<>());
        p8.setAircraftList(new ArrayList<>());

        passengerRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8));

        // make a plane!
        Aircraft ac1 = new Aircraft();
        ac1.setType("Airbus A320");
        ac1.setAirlineName("Air Canada");
        ac1.setPassengers(Arrays.asList(p1, p2, p3)); // Richard, Lucas, Muhammad
        ac1.setNumberOfPassengers(3);
        ac1.setAirports(Arrays.asList(yyt, yhz)); // YYT, YHZ

        Aircraft ac2 = new Aircraft();
        ac2.setType("Airbus A320");
        ac2.setAirlineName("Porter");
        ac2.setPassengers(Arrays.asList(p7, p8)); // Alex, Ben
        ac2.setNumberOfPassengers(2);
        ac2.setAirports(Arrays.asList(yul, yow)); // YUL, YOW

        Aircraft ac3 = new Aircraft();
        ac3.setType("Boeing 747");
        ac3.setAirlineName("West Jet");
        ac3.setPassengers(Arrays.asList(p4, p5, p6)); // Abigail, Steve, Chris
        ac3.setNumberOfPassengers(3);
        ac3.setAirports(Arrays.asList(yxh, yzf)); // YXH, YZF

        // aircraft lists for each of the passenger
        p1.getAircraftList().add(ac1);
        p2.getAircraftList().add(ac1);
        p3.getAircraftList().add(ac1);
        p4.getAircraftList().add(ac3);
        p5.getAircraftList().add(ac3);
        p6.getAircraftList().add(ac3);
        p7.getAircraftList().add(ac2);
        p8.getAircraftList().add(ac2);

        // aircraft list for each airport
        yyt.setAircraft(new ArrayList<>(Collections.singletonList(ac1)));
        yhz.setAircraft(new ArrayList<>(Collections.singletonList(ac1)));
        yul.setAircraft(new ArrayList<>(Collections.singletonList(ac2)));
        yow.setAircraft(new ArrayList<>(Collections.singletonList(ac2)));
        yxh.setAircraft(new ArrayList<>(Collections.singletonList(ac3)));
        yzf.setAircraft(new ArrayList<>(Collections.singletonList(ac3)));

        // save all the things
        aircraftRepository.saveAll(Arrays.asList(ac1, ac2, ac3));
        passengerRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8));
        airportRepository.saveAll(Arrays.asList(yyt, yhz, yul, yow, yxh, yzf));
    }
}



