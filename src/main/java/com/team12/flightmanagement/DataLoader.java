package com.team12.flightmanagement;

import com.team12.flightmanagement.entity.*;
import com.team12.flightmanagement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile; // prevent running in tests
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("!test") // do not run when spring.profiles.active includes "test"
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
                f1e.setDepartedAt(LocalDateTime.now().minusDays(1).withHour(7).withMinute(30));
                f1e.setArrivedAt(LocalDateTime.now().minusDays(1).withHour(9).withMinute(5));

                Flight f2e = new Flight();
                f2e.setAircraft(ac2Existing);
                f2e.setFromAirport(yhzExisting);
                f2e.setToAirport(yulExisting);
                f2e.setDepartedAt(LocalDateTime.now().minusDays(1).withHour(10).withMinute(30));
                f2e.setArrivedAt(LocalDateTime.now().minusDays(1).withHour(11).withMinute(45));

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
        halifax.setPopulation(440000);

        City montreal = new City();
        montreal.setName("Montreal");
        montreal.setProvince("QC");
        montreal.setPopulation(1780000);

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
        yyt.setCode("YYT");
        yyt.setName("St. John's International Airport");
        yyt.setCity(stJohns);

        Airport yhz = new Airport();
        yhz.setCode("YHZ");
        yhz.setName("Halifax Stanfield International Airport");
        yhz.setCity(halifax);

        Airport yul = new Airport();
        yul.setCode("YUL");
        yul.setName("Montréal–Trudeau International Airport");
        yul.setCity(montreal);

        Airport yow = new Airport();
        yow.setCode("YOW");
        yow.setName("Ottawa Macdonald–Cartier International Airport");
        yow.setCity(ottawa);

        Airport yxh = new Airport();
        yxh.setCode("YXH");
        yxh.setName("Medicine Hat Regional Airport");
        yxh.setCity(medicineHat);

        Airport yzf = new Airport();
        yzf.setCode("YZF");
        yzf.setName("Yellowknife Airport");
        yzf.setCity(yellowknife);

        airportRepository.saveAll(List.of(yyt, yhz, yul, yow, yxh, yzf));

        // Passengers
        Passenger alice = new Passenger();
        alice.setFirstName("Alice");
        alice.setLastName("Smith");
        alice.setPhoneNumber("709-555-1111");
        alice.setCity(stJohns);

        Passenger bob = new Passenger();
        bob.setFirstName("Bob");
        bob.setLastName("Brown");
        bob.setPhoneNumber("902-555-2222");
        bob.setCity(halifax);

        Passenger cara = new Passenger();
        cara.setFirstName("Cara");
        cara.setLastName("Nguyen");
        cara.setPhoneNumber("514-555-3333");
        cara.setCity(montreal);

        Passenger dan = new Passenger();
        dan.setFirstName("Dan");
        dan.setLastName("Lee");
        dan.setPhoneNumber("506-256-1234");
        dan.setCity(ottawa);

        passengerRepository.saveAll(List.of(alice, bob, cara, dan));

        // Aircraft
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

        // Link passengers to aircraft (set on aircraft side)
        ac1.setPassengers(new ArrayList<>(List.of(alice, bob)));
        ac2.setPassengers(new ArrayList<>(List.of(alice, cara)));
        ac3.setPassengers(new ArrayList<>(List.of(bob, dan)));
        aircraftRepository.saveAll(List.of(ac1, ac2, ac3));

        // NOTE: We intentionally do NOT call passenger.setAircrafts(...)
        // because Passenger doesn't expose that mutator in your model.
        // Setting from the aircraft side is sufficient for seeding.
        // If you want bi-directional consistency in memory, we can add
        // a safe helper later that checks methods and updates both sides.

        // Flights (sample past flights so arrivedAt is set)
        Flight f1 = new Flight();
        f1.setAircraft(ac1);
        f1.setFromAirport(yyt);
        f1.setToAirport(yhz);
        f1.setDepartedAt(LocalDateTime.now().minusDays(1).withHour(7).withMinute(30));
        f1.setArrivedAt(LocalDateTime.now().minusDays(1).withHour(9).withMinute(5));

        Flight f2 = new Flight();
        f2.setAircraft(ac2);
        f2.setFromAirport(yhz);
        f2.setToAirport(yul);
        f2.setDepartedAt(LocalDateTime.now().minusDays(1).withHour(10).withMinute(30));
        f2.setArrivedAt(LocalDateTime.now().minusDays(1).withHour(11).withMinute(45));

        Flight f3 = new Flight();
        f3.setAircraft(ac3);
        f3.setFromAirport(yzf);
        f3.setToAirport(yxh);
        f3.setDepartedAt(LocalDateTime.now().minusDays(1).withHour(8).withMinute(45));
        f3.setArrivedAt(LocalDateTime.now().minusDays(1).withHour(10).withMinute(55));

        flightRepository.saveAll(List.of(f1, f2, f3));
    }

    private Airport findAirportByCode(String code) {
        for (Airport a : airportRepository.findAll()) {
            if (code.equalsIgnoreCase(a.getCode())) return a;
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






