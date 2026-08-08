package com.cleancode.ecommerce.customer.domain.customer;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.cleancode.ecommerce.customer.domain.address.Charge;
import com.cleancode.ecommerce.customer.domain.address.Delivery;
import com.cleancode.ecommerce.customer.domain.card.Card;
import com.cleancode.ecommerce.customer.domain.card.Code;
import com.cleancode.ecommerce.customer.domain.card.ExpirationDate;
import com.cleancode.ecommerce.customer.domain.card.Flag;
import com.cleancode.ecommerce.customer.domain.card.NumberCard;
import com.cleancode.ecommerce.customer.domain.card.PrintedName;
import com.cleancode.ecommerce.customer.domain.contact.Contact;
import com.cleancode.ecommerce.customer.domain.contact.Phone;
import com.cleancode.ecommerce.customer.domain.contact.TypePhone;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.shared.kernel.Cpf;
import com.cleancode.ecommerce.shared.kernel.Email;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Password;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Customer {

	private CustomerId id;
	private boolean active = false;
	private SystemClientStatus systemClientStatus;
	private Name name;
	private Gender gender;
	private Birth birth;
	private Cpf cpf;
	private Contact contact;
	private Password password;
	private List<Delivery> deliveries = new ArrayList<>();
	private List<Charge> charges = new ArrayList<>();
	private List<Card> cards = new ArrayList<>();

	public Customer(CustomerId id, Name name, Gender gender, Birth birth, Cpf cpf, Contact contact, Password password,
			SystemClientStatus systemClientStatus) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.cpf = cpf;
		this.contact = contact;
		this.password = password;
		this.systemClientStatus = systemClientStatus;
	}

	public void assignId(String id) {
		this.id = new CustomerId(id);
	}

	public void updateCustomer(String name, LocalDate birth, String ddd, String phone, TypePhone typePhone) {
		if (name != null && !name.isBlank()) {
			this.name = new Name(name);
		}

		if (birth != null) {
			this.birth = new Birth(birth);
		}

		if (ddd != null && !ddd.isBlank()) {
			this.contact = new Contact(new Phone(ddd, this.contact.getPhone(), this.contact.getTypePhone()),
					contact.getEmail());
		}

		if (phone != null && !phone.isBlank()) {
			this.contact = new Contact(new Phone(this.contact.getDDD(), phone, this.getContact().getTypePhone()),
					contact.getEmail());
		}

		if (typePhone != null) {
			this.contact = new Contact(new Phone(this.contact.getDDD(), this.contact.getPhone(), typePhone),
					contact.getEmail());
		}
	}

	public void updatePassword(String password) {
		if (password != null && !password.isBlank()) {
			this.password = new Password(password);
		}
	}

	private boolean meetsActivationCriteria() {
		return !charges.isEmpty() && !deliveries.isEmpty();
	}

	public void changeCustomerActivationStatusImpl() {
		this.systemClientStatus = SystemClientStatus.changeStatus(this.systemClientStatus.isSystemClientStatus());
	}

	public boolean checkActivationRequirements() {
		this.active = meetsActivationCriteria();
		return this.active;
	}

	public boolean getSystemClientStatus() {
		return systemClientStatus.isSystemClientStatus();
	}

	public Email getEmail() {
		return this.contact.getEmail();
	}

	public String getEmailAuth() {
		return this.contact.getEmail().getEmail();
	}

	public Phone getFullPhone() {
		return this.contact.getFullPhone();
	}

	public void registerCard(boolean main, String printedName, String code, String numberCard, LocalDate expirationDate,
			Flag flag) {

		Card card = new Card(main, new PrintedName(printedName), new Code(code), new NumberCard(numberCard),
				new ExpirationDate(expirationDate), flag);

		registerCard(card);
	}

	private void registerCard(Card newCard) {
	    if (newCard.isMain()) {

	    	this.cards.stream()
	            .filter(Card::isMain)
	            .forEach(Card::disableMain); 
	    }
	    this.cards.add(newCard);
	}

	public void restoreCard(Card savedCard) {
		this.cards.add(savedCard);
	}
	
	public void registerDelivery(String receiver, Boolean main, String street, String number, String neighborhood,
			String zipCode, String observation, String streetType, String typeResidence, String city, String state,
			String country, String deliveryPhrase) {
		
		Delivery delivery = new Delivery(UUID.randomUUID().toString(), main, deliveryPhrase, receiver, street, number, neighborhood, zipCode, observation, streetType, typeResidence, city, state, country);
		registerDelivery(delivery);
	}

	private void registerDelivery(Delivery newDelivery) {
		if (newDelivery.isMain()) {

	    	this.deliveries.stream()
	            .filter(Delivery::isMain)
	            .forEach(Delivery::disableMain); 
	    }
	    this.deliveries.add(newDelivery);
	}
	
	public void restoreDelivery(Delivery savedDelivery) {
		this.deliveries.add(savedDelivery);
	}
	
	public void registerCharge(String receiver, Boolean main, String street, String number, String neighborhood, String zipCode,
							   String observation, String streetType, String typeResidence, String city, String state, String country) {
		
		Charge charge = new Charge(UUID.randomUUID().toString(), main, receiver, street, number, neighborhood, zipCode, observation, streetType, typeResidence,
								   city, state, country);
		
		registerCharge(charge);
	}

	private void registerCharge(Charge newCharge) {
		if (newCharge.isMain()) {

	    	this.charges.stream()
	            .filter(Charge::isMain)
	            .forEach(Charge::disableMain); 
	    }
	    this.charges.add(newCharge);
	}
	
	public void restoreCharge(Charge savedCharge) {
		this.charges.add(savedCharge);
	}

	public Delivery findDeliveryById(String id) {
		if (id == null || id.isBlank()) {
			throw new IllegalDomainException("Delivery ID must not be null or blank");
		}

		return deliveries.stream().filter(d -> d.getPublicId().equals(id)).findFirst()
				.orElseThrow(() -> new IllegalDomainException("Id Delivery not found"));
	}

	public void removeDeliveryById(String id) {
		if (id == null || id.isBlank() || this.deliveries == null) {
			throw new IllegalDomainException(
					"Cannot remove delivery: id is null/empty or delivery list is not initialized");
		}

		this.deliveries.removeIf(d -> d.getPublicId().equals(id));
	}

	public Delivery findMainDelivery() {
		return deliveries.stream().filter(Delivery::isMain).findFirst()
				.orElseThrow(() -> new IllegalDomainException("Not find delivery main"));
	}

	public Charge findMainCharge() {
		return charges.stream().filter(Charge::isMain).findFirst()
				.orElseThrow(() -> new IllegalDomainException("Not find charge main"));
	}

	public Charge findChargeById(String id) {
		if (id == null || id.isBlank()) {
			throw new IllegalDomainException("Charge ID must not be null or blank");
		}

		return charges.stream().filter(c -> c.getPublicId().equals(id)).findFirst()
				.orElseThrow(() -> new IllegalDomainException("Id Charge not found"));
	}

	public void removeChargeById(String id) {
		if (id == null || id.isBlank() || this.charges == null) {
			throw new IllegalDomainException(
					"Cannot remove charge: id is null/empty or Charge list is not initialized");
		}

		this.charges.removeIf(c -> id.equals(c.getPublicId()));
	}

	public boolean isActive() {
		return active;
	}

	public CustomerId getId() {
		return id;
	}

	public Name getName() {
		return name;
	}

	public Password getPassword() {
		return password;
	}

	public Gender getGender() {
		return gender;
	}

	public Birth getBirth() {
		return birth;
	}

	public Cpf getCpf() {
		return cpf;
	}

	public Contact getContact() {
		return contact;
	}

	public List<Delivery> getDeliverys() {
		return Collections.unmodifiableList(this.deliveries);
	}

	public List<Charge> getCharges() {
		return Collections.unmodifiableList(this.charges);
	}

	public List<Card> getCards() {
		return Collections.unmodifiableList(this.cards);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(id, other.id);
	}
}