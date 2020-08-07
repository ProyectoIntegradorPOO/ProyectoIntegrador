package igu.articles;


import data.ProductoData;

import entities.Producto;


import java.util.ArrayList;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Asullom
 */
public class ProductosTablesModel extends AbstractTableModel {

    private List<Producto> lis = new ArrayList();
    private String[] columns = {"#", "nombre", "idmarca", "marca","precio"};
    private Class[] columnsType = {Integer.class, String.class, Integer.class, String.class, Double.class, };
    
   
    
   
    
    
    
    public ProductosTablesModel() {
        lis = ProductoData.list("");
    }

    public ProductosTablesModel(String filter) {
        lis = ProductoData.list(filter);
    }
    /*public ProductosTablesModel(Producto d){
        this.lis = ProductoData.listArticlesById(d.getIdProducto());
        this.lis.add(new Producto());
    }*/

    @Override
    public Object getValueAt(int row, int column) {
        Producto d = (Producto) lis.get(row);
        //String fecha=iguSDFff.format(d.getFecha_ingreso());
        switch (column) {
            case 0:
                return row + 1;
            case 1:
                return d.getNomProducto();
            case 2:
                return d.getIdMarca();
            case 3:
                return d.getNomMarca();
            case 4:
                return d.getPrecio();
          
            default:
                return null;
        }
    }
/*
    @Override
    public void setValueAt(Object valor, int row, int column) {
        Articulos d = (Articulos) lis.get(row);
        switch (column) {
            
           // case 0:
           //     int id = 0;
           //     try {
            //        if (valor instanceof Number) {
           //             id = Integer.parseInt("" + valor);
           //         }
           //     } catch (NumberFormatException nfe) {
            //        System.err.println("" + nfe);
             //   }
            //    d.setId(id);
             //   break;
             
            case 1:
                d.setNombres("" + valor);
                break;
            case 2:
                d.setInfoadic("" + valor);
                break;

        }
        this.fireTableRowsUpdated(row, row);
        //fireTableCellUpdated(row, row);
    }
*/
    @Override
    public boolean isCellEditable(int row, int column) {
        //Articulos c = (Articulos) lis.get(row);
        if (column >= 0 && column != 0) {
            //return true;
        }
        return false;//bloquear edicion
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class getColumnClass(int column) {
        return columnsType[column];
    }

    @Override
    public int getRowCount() {
        return lis.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    public void addRow(Producto d) { // con db no se usa
        this.lis.add(d);
        //this.fireTableDataChanged();
        this.fireTableRowsInserted(lis.size(), lis.size());
    }

    public void removeRow(int linha) { // con db no se usa
        this.lis.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }

    public Object getRow(int row) { // usado para paintForm
        this.fireTableRowsUpdated(row, row);
        return lis.get(row);
    }

}
