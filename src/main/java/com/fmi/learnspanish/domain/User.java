// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USERS")
@Getter
@Setter
public class User implements UserDetails {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(length = 36)
	private String id;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "MAIN_LEVEL")
	@Enumerated(EnumType.STRING)
	private MainLevel level;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "GRAMMAR_LEVEL_ID", referencedColumnName = "ID")
	private GrammarLevel grammarLevel;

	@ManyToOne(optional = false)
	@JoinColumn(name = "VOCABULARY_LEVEL_ID", referencedColumnName = "ID")
	private VocabularyLevel vocabularyLevel;

	@ManyToOne(optional = false)
	@JoinColumn(name = "PRACTICE_LEVEL_ID", referencedColumnName = "ID")
	private PracticeLevel practiceLevel;

	@Column(name = "is_account_non_expired")
	private boolean isAccountNonExpired = true;

	@Column(name = "is_account_non_locked")
	private boolean isAccountNonLocked = true;

	@Column(name = "is_credentials_non_expired")
	private boolean isCredentialsNonExpired = true;

	@Column(name = "is_enabled")
	private boolean isEnabled = true;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> authorities;

	public User() {
		authorities = new HashSet<>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		User other = (User) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}

		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}

		return true;
	}

}
