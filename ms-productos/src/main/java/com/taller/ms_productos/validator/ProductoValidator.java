package com.taller.ms_productos.validator;

import com.taller.ms_productos.common.exceptions.NombreDuplicadoException;
import com.taller.ms_productos.common.exceptions.ProductoConStockException;
import com.taller.ms_productos.common.exceptions.StockInsuficienteException;
import com.taller.ms_productos.model.Producto;
import com.taller.ms_productos.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductoValidator {

    private final ProductoRepository productoRepository;

    /*Regla 1: No pueden existir dos productos con el mismo nombre
     * (ignorando mayusculas y minusculas)
     */
    public void checkNombreUnico(String nombre) {
        if (productoRepository.existsByNombreIgnoreCase(nombre)) {
            throw new NombreDuplicadoException(nombre);
        }
    }

    /*Regla 1 (variante para actualización): No pueden existir dos productos con el mismo nombre
     * (ignorando mayusculas y minusculas)
     */
    public void checkNombreUnicoUpdate(Producto productoActual, String nuevoNombre) {
        boolean nombreCambiado = !productoActual.getNombre().equalsIgnoreCase(nuevoNombre);
        if (nombreCambiado && productoRepository.existsByNombreIgnoreCase(nuevoNombre)) {
            throw new NombreDuplicadoException(nuevoNombre);
        }
    }

    //Regla 2: No se puede descontar mas stock del que hay disponible
    public void checkStockSuficiente(Producto producto, int cantidad) {
        if (producto.getStock() < cantidad) {
            throw new StockInsuficienteException(
                    "Stock insuficiente. Disponible: " + producto.getStock() + ", Solicitado: " + cantidad
            );
        }
    }

    //Regla 3: No se puede eliminar un producto que todavia tiene stock (stock > 0)
    public void checkProductoSinStock(Producto producto) {
        if (producto.getStock() > 0) {
            throw new ProductoConStockException(
                    "No se puede eliminar el producto porque tiene stock disponible: " + producto.getStock()
            );
        }
    }
}
