package in.app.heal.service;

import in.app.heal.entities.PublicQNA;
import in.app.heal.repository.PublicQNARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicQNAService {
    @Autowired
    private PublicQNARepository repository;


    public PublicQNA addQuestion(PublicQNA question){
        repository.save(question);
        return question;
    }
    public Optional<PublicQNA> findById(Integer questionId){
        return repository.findById(questionId);
    }
    public void deleteById(Integer questionId){
        repository.deleteById(questionId);
    }
    public Optional<List<PublicQNA>> findAll(){
        return repository.findAllOrdered();
    }
}
