package bts.sio.api.controller;

import bts.sio.api.model.Sport;
import bts.sio.api.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.Optional;

@RestController
@Tag(name = "Sports", description = "API pour la gestion des sports")
public class SportController {

    @Autowired
    private SportService sportService;

    /**
     * Create - Add a new sport
     * @param sport An object sport
     * @return The sport object saved
     */
    @Operation(
            summary = "Ajouter un sport",
            description = "Permet d'ajouter un sport"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sport ajouté avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "409", description = "Sport existe déjà"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping("/sport")
    public Sport createSport(@RequestBody Sport sport) {
        return sportService.saveSport(sport);
    }


    /**
     * Read - Get one sport
     * @param id The id of the sport
     * @return An sport object full filled
     */

    @Operation(
            summary = "Récupérer un sport",
            description = "Permet de récupérer un sport par son ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sport trouvé"),
            @ApiResponse(responseCode = "404", description = "Sport non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/sport/{id}")
    public Sport getSport(@PathVariable("id") final Long id) {
        Optional<Sport> sport = sportService.getSport(id);
        if(sport.isPresent()) {
            return sport.get();
        } else {
            return null;
        }
    }

    /**
     * Read - Get all sports
     * @return - An Iterable object of Sport full filled
     */

    @Operation(
            summary = "Récupérer tous les sports",
            description = "Permet de récupérer la liste de tous les sports"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des sports récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/sports")
    public Iterable<Sport> getSports() {
        return sportService.getSports();
    }

    /**
     * Update - Update an existing sport
     * @param id - The id of the sport to update
     * @param sport - The sport object updated
     * @return
     */

    @Operation(
            summary = "Mettre à jour un sport",
            description = "Permet de modifier un sport existant"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sport mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Sport non trouvé"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PutMapping("/sport/{id}")
    public Sport updateSport(@PathVariable("id") final Long id, @RequestBody Sport sport) {
        Optional<Sport> s = sportService.getSport(id);
        if(s.isPresent()) {
            Sport currentSport = s.get();

            String nom = sport.getNom();
            if(nom != null) {
                currentSport.setNom(nom);
            }
            sportService.saveSport(currentSport);
            return currentSport;
        } else {
            return null;
        }
    }


    /**
     * Delete - Delete an sport
     * @param id - The id of the sport to delete
     */

    @Operation(
            summary = "Supprimer un sport",
            description = "Permet de supprimer un sport existant"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Sport supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Sport non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @DeleteMapping("/sport/{id}")
    public void deleteSport(@PathVariable("id") final Long id) {
        sportService.deleteSport(id);
    }

}

