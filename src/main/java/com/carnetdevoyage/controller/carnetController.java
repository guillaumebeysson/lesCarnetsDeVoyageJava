package com.carnetdevoyage.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carnetdevoyage.models.Carnet;
import com.carnetdevoyage.repositories.CarnetRepository;
import com.carnetdevoyage.services.CarnetService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/carnets")
@AllArgsConstructor
public class carnetController {

	private final CarnetService carnetService;
	private CarnetRepository carnetRepository;
	private EntityManager entityManager;
	
	@PostMapping("/createCarnet")
	public Carnet create(@RequestBody Carnet carnet) {
		return carnetService.save(carnet);
	}
	
/*	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")*/
	@GetMapping("")
	public Collection<Carnet> read(){
		return carnetService.findAll();
	}
	
	@GetMapping("/reverseOrder")
	public Collection<Carnet> findAllReverseOrder(){
		return carnetService.findAllReverseOrder();
	}
	
	@GetMapping("/{id}")
	public Optional<Carnet> readById(@PathVariable Long id){
		return carnetService.findById(id);
	}
	
	
	@GetMapping("/lastFour")
	public Collection<Carnet> readLastFour(){
		List<Carnet> carnets = new ArrayList<>(carnetService.findAll());
		Collections.reverse(carnets);
		int endIndex = Math.min(carnets.size(), 4);
	    return carnets.subList(0, endIndex);
	}
	
	@GetMapping("/randomCarnet")
	public Carnet getRandomCarnet() {
	    Collection<Carnet> carnets = carnetService.findAll();
	    int randomIndex = new Random().nextInt(carnets.size());
	    return carnets.stream().skip(randomIndex).findFirst().get();
	}
	
	@GetMapping("country/{country}")
	public Collection<Carnet> readByCountry(@PathVariable String country){
		return carnetService.findByCountry(country);
	}
	
	@PutMapping("/update/{id}")
	public Carnet update(@PathVariable Long id, @RequestBody Carnet carnet) {
		return carnetService.updaCarnet(id, carnet);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
	    carnetService.deleteById(id);
	    return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/search")
    public List<Carnet> rechercherCarnets(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer durationTrip,
            @RequestParam(required = false) String organisation,
            @RequestParam(required = false) String situation,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String transport,
            @RequestParam(required = false) String departurePeriod
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Carnet> query = cb.createQuery(Carnet.class);
        Root<Carnet> root = query.from(Carnet.class);
        List<Predicate> predicates = new ArrayList<>();

        if (country != null) {
            predicates.add(cb.equal(root.get("country"), country));
        }
        if (durationTrip != null) {
            predicates.add(cb.equal(root.get("durationTrip"), durationTrip));
        }
        if (organisation != null) {
            predicates.add(cb.equal(root.get("organisation"), organisation));
        }
        if (situation != null) {
            predicates.add(cb.equal(root.get("situation"), situation));
        }
        if (title != null) {
            predicates.add(cb.equal(root.get("title"), title));
        }
        if (transport != null) {
            predicates.add(cb.equal(root.get("transport"), transport));
        }
        if (departurePeriod != null) {
            predicates.add(cb.equal(root.get("departurePeriod"), departurePeriod));
        }

        query.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Carnet> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
