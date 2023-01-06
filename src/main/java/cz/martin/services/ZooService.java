package cz.martin.services;

import cz.martin.models.VetAnimalsCount;
import cz.martin.models.VetWithAnimals;
import cz.martin.repositories.ZooRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
@ApplicationScoped
public class ZooService {
    @Inject
    private ZooRepository zooRepository;

    public List<String> getVet() {
        return zooRepository.getVet();
    }

    public List<VetAnimalsCount> getCount() {
        return zooRepository.getAnimalsCount();
    }

    public List<VetWithAnimals> getVetsWithAnimals() {
        return zooRepository.getVetWithAnimals();
    }

    public List<VetWithAnimals> getFiltered(boolean maRad, boolean osetruje) {
        return zooRepository.getFiltered(maRad, osetruje);
    }

    /*
    public List<VetWithAnimals> getVetWithAnimalsLike() {
        return zooRepository.getVetWithAnimalsLike();
    }

    public List<VetWithAnimals> getVetWithAnimalsLikeOnly() {
        return zooRepository.getVetWithAnimalsLikeOnly();
    }
    */
}
