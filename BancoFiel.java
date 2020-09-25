package aed.bancofiel;

import java.util.Comparator;
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




  // ----------------------------------------------------------------------
  // NOTAD. No se deberia cambiar este metodo.
  
  public String toString() {
    return "banco";
  }
  

@Override

public IndexedList<Cuenta> getCuentasOrdenadas(Comparator<Cuenta> cmp) {
	
	IndexedList<Cuenta> ans = cuentas;
	    int n = ans.size();
	    Cuenta aux;
	    for(int i=0; i<n; i++) {
	        for(int j=1; j < (n-1); j++) {
	            if(cmp.compare(ans.get(j-1), ans.get(j))>0){
	                aux = cuentas.get(j-1);
	                ans.set(j-1, cuentas.get(j));
	                ans.set(j, aux);
	            }
	        }

	    }
	    return ans;

}


private int buscarCuenta (String id) throws CuentaNoExisteExc{
	boolean res=false;
	int i;
	for(i=0;i<cuentas.size() && !res;i++) {
		if(cuentas.get(i).getId().equals(id)) {
			res=true;
		}
	}
	if (res==false) {
		throw new CuentaNoExisteExc();
	}
	return i-1;
}




@Override
public String crearCuenta(String dni, int saldoIncial) {
	Cuenta res=new Cuenta(dni, saldoIncial);
	return ""+ res.getId();

}

@Override
public void borrarCuenta(String id) throws CuentaNoExisteExc, CuentaNoVaciaExc {	

	if(cuentas.get(buscarCuenta(id)).getSaldo()==0) 
		cuentas.removeElementAt(buscarCuenta(id));
	else
		throw new CuentaNoVaciaExc();
}
		
/*	boolean res=false;
	
	for(int i=0; i<cuentas.size() && !res;i++) {
		
		if(cuentas.get(i).getId().equals(id)) {	

			if(cuentas.get(i).getSaldo()==0) {
				cuentas.removeElementAt(i);
				res=true;
			}else {
				throw new CuentaNoVaciaExc();
			}
		}
	}
	if(res==false) {
		throw new CuentaNoExisteExc();
	}
}*/


@Override
public int ingresarDinero(String id, int cantidad) throws CuentaNoExisteExc {
	cuentas.get(buscarCuenta(id)).ingresar(cantidad);
	return cuentas.get(buscarCuenta(id)).getSaldo();
}
/*
	boolean res=false;
	int i;
	for(i =0 ;i<cuentas.size() && !res;i++) {
		if(cuentas.get(i).getId().equals(id)) {
			cuentas.get(i).ingresar(cantidad);
			res=true;
		}

	}
	
	if(res==false) {
		throw new CuentaNoExisteExc();
	}
	return cuentas.get(i-1).getSaldo();
}*/

@Override
public int retirarDinero(String id, int cantidad) throws CuentaNoExisteExc, InsuficienteSaldoExc {
/*	cuentas.get(buscarCuenta(id)).retirar(cantidad);
	return cuentas.get(buscarCuenta(id)).getSaldo();
}
*/

boolean res=false;
	int i;
	for(i =0 ;i<cuentas.size() && !res;i++) {
		if(cuentas.get(i).getId().equals(id)) {
			
			cuentas.get(i).retirar(cantidad);
			res=true;
		}
	}
	if(res==false) {
		throw new CuentaNoExisteExc();
	}
	return cuentas.get(i-1).getSaldo();
}
	
	


@Override
public int consultarSaldo(String id) throws CuentaNoExisteExc {
/*	return cuentas.get(buscarCuenta(id)).getSaldo();

}*/
	boolean res=false;
	int i;
	for(i =0 ;i<cuentas.size() && !res;i++) {
		if(cuentas.get(i).getId().equals(id)) {
			res=true;
		}

	}
	
	if(res==false) {
		throw new CuentaNoExisteExc();
	}
	return cuentas.get(i-1).getSaldo();
}


@Override
public void hacerTransferencia(String idFrom, String idTo, int cantidad)
		throws CuentaNoExisteExc, InsuficienteSaldoExc {
	boolean res=false;
	boolean sol=false;
	int i;
	int j=0;
	for(i=0;i<cuentas.size() && !res;i++) {	
		if(cuentas.get(i).getId().equals(idFrom)) {

			for(j=i;j<cuentas.size() && !sol;j++);	
			if(cuentas.get(j).getId().equals(idTo)) {		
				sol=true;
			}
		}		
	}			
	if(sol==true && res==true) {
		cuentas.get(i).retirar(cantidad);
		cuentas.get(j).ingresar(cantidad);
	}else {
		throw new CuentaNoExisteExc();
	}
}



@Override
public IndexedList<String> getIdCuentas(String dni) {
	IndexedList<String> res = new ArrayIndexedList<String>();
	int j=0;
	for(int i=0;i<res.size();i++) {
		if(cuentas.get(i).getDNI().equals(dni)){
			res.add(j,cuentas.get(i).getId());
			j++;
		}
	}
	return res;
}

@Override
public int getSaldoCuentas(String dni) {
	int res=0;
	for(int i=0;i<cuentas.size();i++) {
		if(cuentas.get(i).getDNI().equals(dni)) {
			res=res + cuentas.get(i).getSaldo();			
		}

	}
	return res;

}
  
}




