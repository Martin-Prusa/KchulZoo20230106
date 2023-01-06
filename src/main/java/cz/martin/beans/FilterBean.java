package cz.martin.beans;

import cz.martin.models.VetWithAnimals;
import cz.martin.services.ZooService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
@RequestScoped
public class FilterBean {
    @Inject
    private ZooService zooService;

    private boolean isMaRad = false;
    private boolean isOsetruje = false;

    public List<VetWithAnimals> getVetWithAnimals() {
        /*
        if(isMaRad && isOsetruje) return zooService.getVetWithAnimalsLike();
        if(isOsetruje) return zooService.getVetsWithAnimals();
        if(isMaRad) return zooService.getVetWithAnimalsLikeOnly();
        return zooService.getVet().stream().map(i -> new VetWithAnimals(i, new String[]{})).toList();
         */
        if(!isMaRad && !isOsetruje) return zooService.getVet().stream().map(i -> new VetWithAnimals(i, new String[]{})).toList();
        return zooService.getFiltered(isMaRad, isOsetruje);
    }

    public boolean isMaRad() {
        return isMaRad;
    }

    public void setMaRad(boolean maRad) {
        isMaRad = maRad;
    }

    public boolean isOsetruje() {
        return isOsetruje;
    }

    public void setOsetruje(boolean osetruje) {
        isOsetruje = osetruje;
    }
}
