/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.ArrayList;
import java.util.Optional;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import modelo.RepositorioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import vista.ActualizarV;
import vista.InformeV;
import vista.Marco;
import vista.MuestraError;

public class ControladorProducto {
    @Autowired
    RepositorioProducto rp;
    ArrayList<Producto> listaProductos;
    Marco m;

    public ControladorProducto(RepositorioProducto rp, Marco m) {
        this.rp = rp;
        this.m = m;
    }

    public void setListaProductos(ArrayList<Producto>listaProductos){
        this.listaProductos = listaProductos;
    }
    
    public Producto agregar(Producto p){
        return rp.save(p);
    }
    
    public Producto actualizar(Producto p){
        return rp.save(p);
    }
    
    public boolean eliminar(Integer id){
        try{
            rp.deleteById(id);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
    
    public ArrayList<Producto> obtenerProductos(){
        return (ArrayList<Producto>) rp.findAll();
    }
    
    public Optional<Producto> obtenerProducto(Integer id){
        return rp.findById(id);
    }
    
    public double totalInventario(){
        
        double total = 0;
        for(Producto p: listaProductos){
            total += p.getPrecio() * p.getInventario();
        }
        return total;
    }
    
        public String productoPrecioMayor(){
        String nombre = listaProductos.get(0).getNombre();
        double precio = listaProductos.get(0).getPrecio();
        for(Producto p: listaProductos){
            if(p.getPrecio()>precio){
                nombre = p.getNombre();
                precio = p.getPrecio();
            }
        }
        return nombre;
        
    }
    
       public String productoPrecioMenor(){
        String nombre = listaProductos.get(0).getNombre();
        double precio = listaProductos.get(0).getPrecio();
        for(Producto p: listaProductos){
            if(p.getPrecio()<precio){
                nombre = p.getNombre();
                precio = p.getPrecio();
            }
        }
        return nombre;
        
    }
    
    
    public double promedioPrecios(){
        
        double suma = 0;
        
        for(Producto p: listaProductos){
            suma += p.getPrecio();
        }
        return suma/(listaProductos.size());
    }
   
    public void eventoAgregar(){
        String nombre = m.getCampoNombre();
        String precio = m.getCampoPrecio();
        String inventario = m.getCampoInventario();
        if(!nombre.equals("") && !precio.equals("") && !inventario.equals("")){
        Producto nuevo = new Producto(null, nombre, Double.parseDouble(precio), Integer.parseInt(inventario));
        listaProductos.add(nuevo);
        agregar(nuevo);
        DefaultTableModel modelo = (DefaultTableModel) m.getInventario().getModel();
        modelo.insertRow(listaProductos.size()-1, new Object[]{nuevo.getNombre(), nuevo.getPrecio(), nuevo.getInventario()});
       }else{
               MuestraError me = new MuestraError();
               me.setVisible(true);
               }
    }
    
    
    
    public void eventoEliminar(){
        int filaEliminar = m.getInventario().getSelectedRow();
        listaProductos.remove(filaEliminar);
        eliminar(listaProductos.get(filaEliminar).getCodigo());
        DefaultTableModel modelo = (DefaultTableModel) m.getInventario().getModel();
        modelo.removeRow(filaEliminar);
    }
    
    public void abrirVentanaAct(){
        ActualizarV a = new ActualizarV();
        a.setControlador(this);
        a.setVisible(true);
        
    }
    
    public void eventoActualizar(ActualizarV v){
        String nombre = v.getCampoNombreA();
        String precio = v.getCampoPrecioA();
        String inventario = v.getCampoInventarioA();
        if(!nombre.equals("") && !precio.equals("") && !inventario.equals("")){
            int filaActualizar = m.getInventario().getSelectedRow();
            DefaultTableModel modelo = (DefaultTableModel) m.getInventario().getModel();
            listaProductos.get(filaActualizar).setInventario(Integer.parseInt(inventario));
            listaProductos.get(filaActualizar).setNombre(nombre);
            listaProductos.get(filaActualizar).setPrecio(Double.parseDouble(precio));
            actualizar(listaProductos.get(filaActualizar));
            modelo.setValueAt(nombre, filaActualizar, 0);
            modelo.setValueAt(Double.parseDouble(precio), filaActualizar, 1);
            modelo.setValueAt(Integer.parseInt(inventario), filaActualizar, 2);
        }else{
            MuestraError me = new MuestraError();
            me.setVisible(true);
        }
    }
    
    public void eventoInforme(){
        InformeV i = new InformeV();
        i.setVisible(true);
        i.setLabelInventario(i.getLabelInventario()+totalInventario());
        i.setLabelMayor(i.getLabelMayor()+productoPrecioMayor());
        i.setLabelMenor(i.getLabelMenor()+productoPrecioMenor());
        i.setLabelPromedio(i.getLabelPromedio()+promedioPrecios());
    }
    
public void inicializaTabla(){
    DefaultTableModel modelo = (DefaultTableModel) m.getInventario().getModel();
    int ind = 0;
    for(Producto p: listaProductos){
       modelo.insertRow(ind, new Object[]{p.getNombre(), p.getPrecio(), p.getInventario()}); 
    ind+=1;
    }
}    
    
}



