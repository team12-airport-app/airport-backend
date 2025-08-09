package com.team12.flightmanagement;

import com.team12.flightmanagement.entity.*;
import com.team12.flightmanagement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final CityRepository cityRepository;
    private final AirportRepository airportRepository;
    private final PassengerRepository passengerRepository;
    private final AircraftRepository aircraftRepository;
    private final FlightRepository flightRepository;

    public DataLoader(CityRepository cityRepository,
                      AirportRepository airportRepository,
                      PassengerRepository passengerRepository,
                      AircraftRepository aircraftRepository,
                      FlightRepository flightRepository) {
        this.cityRepository = cityRepository;
        this.airportRepository = airportRepository;
        this.passengerRepository = passengerRepository;
        this.aircraftRepository = aircraftRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // If data already exists but flights are missing, seed ONLY flights and exit.
        boolean hasAnyCoreData =
                cityRepository.count() > 0 ||
                        airportRepository.count() > 0 ||
                        passengerRepository.count() > 0 ||
                        aircraftRepository.count() > 0;

        if (hasAnyCoreData && flightRepository.count() == 0) {
            // Lookup existing airports by code
            Airport yytExisting = findAirportByCode("YYT");
            Airport yhzExisting = findAirportByCode("YHZ");
            Airport yulExisting = findAirportByCode("YUL");
            Airport yowExisting = findAirportByCode("YOW");
            Airport yxhExisting = findAirportByCode("YXH");
            Airport yzfExisting = findAirportByCode("YZF");

            // Lookup existing aircraft by airline name
            Aircraft ac1Existing = findAircraftByAirline("Air Canada");
            Aircraft ac2Existing = findAircraftByAirline("Porter");
            Aircraft ac3Existing = findAircraftByAirline("WestJet");

            if (yytExisting != null && yhzExisting != null &&
                    yulExisting != null && yowExisting != null &&
                    yxhExisting != null && yzfExisting != null &&
                    ac1Existing != null && ac2Existing != null && ac3Existing != null) {

                Flight f1e = new Flight();
                f1e.setAircraft(ac1Existing);
                f1e.setFromAirport(yytExisting);
                f1e.setToAirport(yhzExisting);
                f1e.setDepartedAt(LocalDateTime.now().minusDays(3).withHour(9).withMinute(0));
                f1e.setArrivedAt(LocalDateTime.now().minusDays(3).withHour(10).withMinute(30));

                Flight f2e = new Flight();
                f2e.setAircraft(ac2Existing);
                f2e.setFromAirport(yulExisting);
                f2e.setToAirport(yowExisting);
                f2e.setDepartedAt(LocalDateTime.now().minusDays(2).withHour(14).withMinute(15));
                f2e.setArrivedAt(LocalDateTime.now().minusDays(2).withHour(15).withMinute(5));

                Flight f3e = new Flight();
                f3e.setAircraft(ac3Existing);
                f3e.setFromAirport(yzfExisting);
                f3e.setToAirport(yxhExisting);
                f3e.setDepartedAt(LocalDateTime.now().minusDays(1).withHour(8).withMinute(45));
                f3e.setArrivedAt(LocalDateTime.now().minusDays(1).withHour(10).withMinute(55));

                flightRepository.saveAll(List.of(f1e, f2e, f3e));
            }

            return; // only flights were needed
        }

        // seed if empty enough to assume a fresh DB
        if (cityRepository.count() > 0 ||
                airportRepository.count() > 0 ||
                passengerRepository.count() > 0 ||
                aircraftRepository.count() > 0 ||
                flightRepository.count() > 0) {
            return;
        }

        // Cities
        City stJohns = new City();
        stJohns.setName("St. John's");
        stJohns.setProvince("NL");
        stJohns.setPopulation(110000);

        City halifax = new City();
        halifax.setName("Halifax");
        halifax.setProvince("NS");
        halifax.setPopulation(430000);

        City montreal = new City();
        montreal.setName("Montreal");
        montreal.setProvince("QC");
        montreal.setPopulation(1700000);

        City ottawa = new City();
        ottawa.setName("Ottawa");
        ottawa.setProvince("ON");
        ottawa.setPopulation(1010000);

        City medicineHat = new City();
        medicineHat.setName("Medicine Hat");
        medicineHat.setProvince("AB");
        medicineHat.setPopulation(65000);

        City yellowknife = new City();
        yellowknife.setName("Yellowknife");
        yellowknife.setProvince("NT");
        yellowknife.setPopulation(20000);

        cityRepository.saveAll(List.of(stJohns, halifax, montreal, ottawa, medicineHat, yellowknife));

        // Airports
        Airport yyt = new Airport();
        yyt.setName("St. John's International");
        yyt.setCode("YYT");
        yyt.setCity(stJohns);

        Airport yhz = new Airport();
        yhz.setName("Halifax Stanfield");
        yhz.setCode("YHZ");
        yhz.setCity(halifax);

        Airport yul = new Airport();
        yul.setName("Montréal–Trudeau");
        yul.setCode("YUL");
        yul.setCity(montreal);

        Airport yow = new Airport();
        yow.setName("Ottawa Macdonald–Cartier");
        yow.setCode("YOW");
        yow.setCity(ottawa);

        Airport yxh = new Airport();
        yxh.setName("Medicine Hat Regional");
        yxh.setCode("YXH");
        yxh.setCity(medicineHat);

        Airport yzf = new Airport();
        yzf.setName("Yellowknife");
        yzf.setCode("YZF");
        yzf.setCity(yellowknife);

        airportRepository.saveAll(List.of(yyt, yhz, yul, yow, yxh, yzf));

        // bidirectional city to Airports
        stJohns.setAirports(new ArrayList<>(List.of(yyt)));
        halifax.setAirports(new ArrayList<>(List.of(yhz)));
        montreal.setAirports(new ArrayList<>(List.of(yul)));
        ottawa.setAirports(new ArrayList<>(List.of(yow)));
        medicineHat.setAirports(new ArrayList<>(List.of(yxh)));
        yellowknife.setAirports(new ArrayList<>(List.of(yzf)));
        cityRepository.saveAll(List.of(stJohns, halifax, montreal, ottawa, medicineHat, yellowknife));

        // passengers and their info
        Passenger alice = new Passenger();
        alice.setFirstName("Chris");
        alice.setLastName("Morrison");
        alice.setPhoneNumber("709-123-1234");
        alice.setCity(stJohns);

        Passenger bob = new Passenger();
        bob.setFirstName("Steve");
        bob.setLastName("Morrison");
        bob.setPhoneNumber("709-123-2345");
        bob.setCity(halifax);

        Passenger cara = new Passenger();
        cara.setFirstName("Cat");
        cara.setLastName("Lover");
        cara.setPhoneNumber("709-222-1313");
        cara.setCity(montreal);

        Passenger dan = new Passenger();
        dan.setFirstName("Jack");
        dan.setLastName("Dawson");
        dan.setPhoneNumber("506-256-1234");
        dan.setCity(ottawa);

        passengerRepository.saveAll(List.of(alice, bob, cara, dan));

        // airplanes
        Aircraft ac1 = new Aircraft();
        ac1.setType("A220-300");
        ac1.setAirlineName("Air Canada");
        ac1.setNumberOfPassengers(136);

        Aircraft ac2 = new Aircraft();
        ac2.setType("Embraer E195-E2");
        ac2.setAirlineName("Porter");
        ac2.setNumberOfPassengers(132);

        Aircraft ac3 = new Aircraft();
        ac3.setType("737 MAX 8");
        ac3.setAirlineName("WestJet");
        ac3.setNumberOfPassengers(174);

        aircraftRepository.saveAll(List.of(ac1, ac2, ac3));

        // link between passengers and aircrafts - there is new ArrayList to avoid UnsupportedOperationException
        ac1.setPassengers(new ArrayList<>(List.of(alice, bob)));
        ac2.setPassengers(new ArrayList<>(List.of(alice, cara)));
        ac3.setPassengers(new ArrayList<>(List.of(bob, dan)));
        aircraftRepository.saveAll(List.of(ac1, ac2, ac3));

        // Keep inverse side if your Passenger owns the relation or if you serialize both ways
        alice.setAircraftList(new ArrayList<>(List.of(ac1, ac2)));
        bob.setAircraftList(new ArrayList<>(List.of(ac1, ac3)));
        cara.setAircraftList(new ArrayList<>(List.of(ac2)));
        dan.setAircraftList(new ArrayList<>(List.of(ac3)));
        passengerRepository.saveAll(List.of(alice, bob, cara, dan));

        // linked planes and airports
        ac1.setAirports(new ArrayList<>(List.of(yyt, yhz)));        // air canada uses YYT, YHZ
        ac2.setAirports(new ArrayList<>(List.of(yul, yow)));        // porter will use YUL, YOW
        ac3.setAirports(new ArrayList<>(List.of(yzf, yxh)));        // westjet uses YZF, YXH
        aircraftRepository.saveAll(List.of(ac1, ac2, ac3));

        // flights takeoff/landing pairs
        Flight f1 = new Flight();
        f1.setAircraft(ac1);
        f1.setFromAirport(yyt);
        f1.setToAirport(yhz);
        f1.setDepartedAt(LocalDateTime.now().minusDays(3).withHour(9).withMinute(0));
        f1.setArrivedAt(LocalDateTime.now().minusDays(3).withHour(10).withMinute(30));

        Flight f2 = new Flight();
        f2.setAircraft(ac2);
        f2.setFromAirport(yul);
        f2.setToAirport(yow);
        f2.setDepartedAt(LocalDateTime.now().minusDays(2).withHour(14).withMinute(15));
        f2.setArrivedAt(LocalDateTime.now().minusDays(2).withHour(15).withMinute(5));

        Flight f3 = new Flight();
        f3.setAircraft(ac3);
        f3.setFromAirport(yzf);
        f3.setToAirport(yxh);
        f3.setDepartedAt(LocalDateTime.now().minusDays(1).withHour(8).withMinute(45));
        f3.setArrivedAt(LocalDateTime.now().minusDays(1).withHour(10).withMinute(55));

        flightRepository.saveAll(List.of(f1, f2, f3));
    }

    // helpers
    private Airport findAirportByCode(String code) {
        for (Airport a : airportRepository.findAll()) {
            if (code.equals(a.getCode())) return a;
        }
        return null;
    }

    private Aircraft findAircraftByAirline(String airline) {
        for (Aircraft a : aircraftRepository.findAll()) {
            if (airline.equals(a.getAirlineName())) return a;
        }
        return null;
    }
}





