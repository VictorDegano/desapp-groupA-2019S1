package ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO;

import ar.edu.unq.desapp.grupoa.model.event.baquita.LoadedGood;

import java.util.List;
import java.util.stream.Collectors;

public class LoadedGoodDTO {

    private Integer loadedGoodId;
    private Integer guestId;
    private Integer goodId;

    public LoadedGoodDTO(Integer guestId, Integer goodId,Integer loadedGoodId) {
        this.guestId = guestId;
        this.goodId = goodId;
        this.loadedGoodId = loadedGoodId;
    }

    public static List<LoadedGoodDTO> fromList(List<LoadedGood> loadedGoods) {
        return loadedGoods.stream().map(LoadedGoodDTO::from).collect(Collectors.toList());
    }

    private static LoadedGoodDTO from(LoadedGood loadedGood) {
        return new LoadedGoodDTO(loadedGood.getGuest().getId(),loadedGood.getGood().getId(),loadedGood.getId());
    }
}
