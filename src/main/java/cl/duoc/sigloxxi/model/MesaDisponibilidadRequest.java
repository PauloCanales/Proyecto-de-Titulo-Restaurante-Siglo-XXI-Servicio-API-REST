package cl.duoc.sigloxxi.model;

import java.util.List;

public class MesaDisponibilidadRequest {
    private Long fkGarzon;
    private List<Long> mesas;
    
	public Long getFkGarzon() {
		return fkGarzon;
	}
	public void setFkGarzon(Long fkGarzon) {
		this.fkGarzon = fkGarzon;
	}
	public List<Long> getMesas() {
		return mesas;
	}
	public void setMesas(List<Long> mesas) {
		this.mesas = mesas;
	}
	
    


}
