/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Namespace;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jplm
 */
public class NamespacesDTO {

    List<NamespaceDTO> all = new ArrayList();

    public NamespacesDTO(List<Namespace> namespaceEntities) {
        namespaceEntities.forEach((ns) -> {
            all.add(new NamespaceDTO(ns));
        });
    }

    public List<NamespaceDTO> getAll() {
        return all;
    }

    public void setAll(List<NamespaceDTO> all) {
        this.all = all;
    }

}
