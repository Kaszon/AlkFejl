package hu.elte.accounting.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.accounting.entities.Actor;
import hu.elte.accounting.repositories.ActorRepository;

@Service
public class ActorService {
    
    @Autowired
    private ActorRepository actorRepository;

    @Transactional(readOnly = true)
    public Iterable<Actor> getAll() {
        return actorRepository.findAll();
    }

    @Transactional
    public ResponseEntity<Actor> register(Actor actor) {
        Optional<Actor> oUser = actorRepository.findByEmail(actor.getEmail());
        if (oUser.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        actor.setId(null);
//        actor.setPassword(passwordEncoder.encode(actor.getPassword()));
//        actor.setRole(User.Role.ROLE_USER);
        return ResponseEntity.ok(actorRepository.save(actor));
    }

    @Transactional
    public ResponseEntity<Actor> updateActor(Integer id, Actor actor) {
        Optional<Actor> oActor = actorRepository.findById(id);
        if (!oActor.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        actor.setId(id);
        return ResponseEntity.ok(actorRepository.save(actor));
    }
    
    @Transactional
    public ResponseEntity<Actor> deleteActor(Integer id) {
        Optional<Actor> oActor = actorRepository.findById(id);
        if (!oActor.isPresent()) {
            return ResponseEntity.notFound().build();   
        }            
        actorRepository.delete(oActor.get());
        return ResponseEntity.ok().build();
    }
}