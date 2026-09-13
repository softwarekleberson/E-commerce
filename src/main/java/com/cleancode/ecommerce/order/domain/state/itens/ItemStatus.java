package com.cleancode.ecommerce.order.domain.state.itens;

public enum ItemStatus {
	
	AWAITING_PAYMENT {
        @Override
        public ItemState createState() {
            return new AwaitingPaymentState();
        }
    },
	
    CANCELLED {
        @Override
        public ItemState createState() {
            return new CancelledState();
        }
    },
    
    SEPARATING {
        @Override
        public ItemState createState() {
            return new SeparatingState();
        }
    },
    
    SHIPPED {
        @Override
        public ItemState createState() {
            return new ShippedState();
        }
    },
    
    DELIVERED {
        @Override
        public ItemState createState() {
            return new DeliveredState();
        }
    },
	
	EXCHANGE_REQUEST {
		@Override
		public ItemState createState() {
			return new ExchangeRequestState();
		}
	},
	
	EXCHANGE_ACCEPTED {
		@Override
		public ItemState createState() {
			return new ExchangeAcceptedState();
		}
	},
	
	EXCHANGE_REJECTED {
		public ItemState createState() {
			return new ExchangeRejectedState();
		}
	};

    // Método abstrato exigido para cada constante do Enum
    public abstract ItemState createState();
}