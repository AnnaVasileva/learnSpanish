package com.fmi.learnspanish.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
}
