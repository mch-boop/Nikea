package negocio.servicio;

import org.json.JSONObject;

public class TArticulo extends TServicio {
	private int idMarca;
	private int ventas;
	
	public void setMarcaId(int marca) {
		this.idMarca = marca;
	}
	
	public int getMarcaId() {
		return idMarca;	 
	}
	
	@Override
	public JSONObject asJSON() {
		JSONObject obj = super.asJSON();
		obj.put("idMarca", this.idMarca);
		obj.put("ventas", this.ventas);
		return obj;
	}
	
	@Override
	public void fromJSON(JSONObject obj) {
		super.fromJSON(obj);
		if (obj == null) return;
		this.idMarca = obj.has("idMarca") && !obj.isNull("idMarca") ? obj.getInt("idMarca") : 0;
		this.ventas = obj.has("ventas") && !obj.isNull("ventas") ? obj.getInt("ventas") : 0;
	}
}
