package com.football.dev.footballapp.controllers;
import com.football.dev.footballapp.dto.ContractDto;
import com.football.dev.footballapp.dto.InjuryDto;
import com.football.dev.footballapp.dto.PageResponseDto;
import com.football.dev.footballapp.services.ContractService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contracts")
@CrossOrigin("http://localhost:4200")
public class ContractsController {
    private final ContractService contractService;
    public ContractsController(ContractService contractService){this.contractService=contractService;}
    @PostMapping("/{playerId}/create")
    public ResponseEntity<String> createContract(@PathVariable("playerId") Long playerId, @RequestBody ContractDto contractDto) {
        contractService.saveContract(contractDto,playerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/{playerId}/{contractId}")
    public ResponseEntity<ContractDto> getContract(@PathVariable("playerId") Long playerId,@PathVariable("contractId") Long contractId) {
        return ResponseEntity.status(HttpStatus.OK).body(contractService.getContract(playerId,contractId));
    }
    @PutMapping("/{playerId}/edit/{contractId}")
    public ResponseEntity<String> editContract(@PathVariable("playerId") Long playerId,@PathVariable("contractId") Long contractId,
                                             @RequestBody ContractDto contractDto) {
        contractService.updateContract(contractDto,contractId,playerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/{playerId}/delete/{contractId}")
    public ResponseEntity<InjuryDto> deleteContract(@PathVariable("playerId") Long playerId,@PathVariable("contractId") Long contractId) {
        contractService.deleteContract(contractId,playerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
//    @GetMapping("/{playerId}/")
//    public ResponseEntity<PageResponseDto<InjuryDto>> getContracts(@PathVariable("playerId") Long playerId, @RequestParam(defaultValue = "0") int page,
//                                                                  @RequestParam(defaultValue = "10") int size) {
//        Page<InjuryDto> injuryDtoPage = injuryService.retrieveInjuries(playerId,page,size);
//        PageResponseDto<InjuryDto> responseDto = new PageResponseDto<>(
//                injuryDtoPage.getContent(),
//                injuryDtoPage.getNumber(),
//                injuryDtoPage.getSize(),
//                injuryDtoPage.getTotalElements()
//        );
//        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
//    }
}
