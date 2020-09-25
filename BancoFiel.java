package aed.bancofiel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.upm.aedlib.indexedlist.IndexedList;
import es.upm.aedlib.indexedlist.ArrayIndexedList;


/**
 * Implements the code for the bank application.
 * Implements the client and the "gestor" interfaces.
 */
public class BancoFiel implements ClienteBanco, GestorBanco {

  // NOTAD. No se deberia cambiar esta declaracion.
  public IndexedList<Cuenta> cuentas;

  // NOTAD. No se deberia cambiar esta constructor.
  public BancoFiel() {
    this.cuentas = new ArrayIndexedList<Cuenta>();
  }

  // ----------------------------------------------------------------------
  // Anadir metodos aqui ...
  @Override
  public IndexedList<Cuenta> getCuentasOrdenadas(Comparator<Cuenta> cmp) {
	IndexedList<Cuenta> ans = cuentas;
	int n = ans.size();
	Cuenta aux;
	for(int i=0; i<n; i++) {
		for(int j=1; j < (n-1); j++) {
			if(cmp.compare(ans.get(j-1), ans.get(j))<0){
				aux = cuentas.get(j-1);
				ans.set(j-1, cuentas.get(j));
				ans.set(j, aux);
			}
		}
		
	}
	return ans;
	
  }

  @Override
  public String crearCuenta(String dni, int saldoIncial) {
  	Cuenta aux = new Cuenta(dni, saldoIncial);
  	return aux.getId();
  }

  @Override
  public void borrarCuenta(String id) throws CuentaNoExisteExc, CuentaNoVaciaExc {
	  boolean aux = false;
	  int pos = -1;
	  for(int i=0; i<cuentas.size() && !aux; i++) {
		  if(id.equals(cuentas.get(i).getId())) {
				cuentas.remove(cuentas.get(i));
				aux = true;
				pos = i;
		  }	
	  }
	  
	  if(pos==-1) {
		  throw new CuentaNoExisteExc();
	  }else if(cuentas.get(pos).getSaldo() != 0) {
		  throw new CuentaNoVaciaExc();
	  }else {
		  cuentas.removeElementAt(pos);
	  }
	  
	  
  	/*try {
  		for(int i=0; i<cuentas.size(); i++) {
  			if(id.equals(cuentas.get(i).getId())) {
  				cuentas.remove(cuentas.get(i));
  			}
  		}
  	}catch(Exception Cuentas) {
  		thro
  	}*/
  	
  }

  @Override
  public int ingresarDinero(String id, int cantidad) throws CuentaNoExisteExc {
	  boolean aux = false;
	  int pos = -1;
	  for(int i=0; i<cuentas.size() && !aux; i++) {
		  if(id.equals(cuentas.get(i).getId())) {
				cuentas.remove(cuentas.get(i));
				aux = true;
				pos = i;
		  }	
	  }
	  if(pos==-1) {
		  throw new CuentaNoExisteExc();
	  }
	  
	  return  cuentas.get(pos).ingresar(cantidad);
  }

  @Override
  public int retirarDinero(String id, int cantidad) throws CuentaNoExisteExc, InsuficienteSaldoExc {
	  boolean aux = false;
	  int pos = -1;
	  for(int i=0; i<cuentas.size() && !aux; i++) {
		  if(id.equals(cuentas.get(i).getId())) {
				cuentas.remove(cuentas.get(i));
				aux = true;
				pos = i;
		  }	
	  }
	  if(pos==-1) {
		  throw new CuentaNoExisteExc();
	  }else if(cuentas.get(pos).getSaldo()<cantidad) {
		  throw new InsuficienteSaldoExc();
	  }else {
		  return cuentas.get(pos).retirar(cantidad);
	  }
  }

  @Override
  public int consultarSaldo(String id) throws CuentaNoExisteExc {
	  boolean aux = false;
	  int pos = -1;
	  for(int i=0; i<cuentas.size() && !aux; i++) {
		  if(id.equals(cuentas.get(i).getId())) {
				cuentas.remove(cuentas.get(i));
				aux = true;
				pos = i;
		  }	
	  }
	  if(pos==-1) {
		  throw new CuentaNoExisteExc();
	  }else {
		  return cuentas.get(pos).getSaldo();
	  }
  }

  @Override
  public void hacerTransferencia(String idFrom, String idTo, int cantidad)
  		throws CuentaNoExisteExc, InsuficienteSaldoExc {
  	boolean from=false;
  	boolean to=false;
  	int posFrom=-1;
  	int posTo=-1;
  	for(int i=0; i<cuentas.size() && !(from || to); i++) {
  		if(idFrom.equals(cuentas.get(i).getId()) && !from) {
  			from = true;
  			posFrom = i;
  		}if(idTo.equals(cuentas.get(i).getId()) && !to) {
  			to = true;
  			posTo = i;
  		}
  	}
  	if(posFrom == -1 || posTo == -1) {
  		throw new CuentaNoExisteExc();
  	}else if(cuentas.get(posFrom).getSaldo()<cantidad) {
  		throw new InsuficienteSaldoExc();
  	}else {
  		cuentas.get(posFrom).retirar(cantidad);
  		cuentas.get(posTo).ingresar(cantidad);
  	}
  	
  }

  @Override
  public IndexedList<String> getIdCuentas(String dni) {
	  IndexedList<String> ans = new ArrayIndexedList<>();
	  int j=0;
	  for(int i=0; i<cuentas.size() ; i++) {
		  if(cuentas.get(i).getDNI().equals(dni)) {
			  ans.add(j++,cuentas.get(i).getId());
		  }
	  }
	  return ans;
  }

  @Override
  public int getSaldoCuentas(String dni) {
  	IndexedList<String> ans = getIdCuentas(dni);
  	int sum=0;
    for(int i=0; i<cuentas.size() ; i++) {
		  if(cuentas.get(i).getDNI().equals(dni)) {
			  sum += cuentas.get(i).getSaldo();
		  }
	  }
    return sum;
  }



  // ----------------------------------------------------------------------
  // NOTAD. No se deberia cambiar este metodo.
  public String toString() {
    return "banco";
  }


  
}



