package com.blog.blog.service;

import com.blog.blog.model.Role;
import com.blog.blog.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Menemukan role berdasarkan ID.
     * @param id ID role.
     * @return Role yang sesuai.
     */

    
    /**
     * Menemukan role berdasarkan nama.
     * @param name Nama role.
     * @return Role yang sesuai.
     */
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    /**
     * Menyimpan role baru atau memperbarui role yang ada.
     * @param role Role yang akan disimpan.
     * @return Role yang telah disimpan.
     */
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    /**
     * Mengambil semua role yang tersedia.
     * @return Daftar semua role.
     */
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
    public Optional<Role> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID tidak boleh null");
        }
        return roleRepository.findById(id);
    }
    
    
}
