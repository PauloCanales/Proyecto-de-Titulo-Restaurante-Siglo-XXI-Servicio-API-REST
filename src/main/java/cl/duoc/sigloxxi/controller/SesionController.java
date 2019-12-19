package cl.duoc.sigloxxi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.sigloxxi.entity.Sesion;
import cl.duoc.sigloxxi.repository.SesionRepository;

@Service
public class SesionController {
	
	@Autowired
	SesionRepository sesionRepository;
	
	public Sesion getSesionByToken(String token) {
		return sesionRepository.findByToken(token);
	}

}
