{
	/*
	
	BASIC RULES
	
	----------------------------
	-- YOU CAN READ AND WRITE --

	this.pedal = 'accelerate', 'brake', ''

	this.wheel = 'right', 'left', ''
	
	-----------------------
	-- YOU CAN ONLY READ --

	info.speed = 0 to 200

	info.curveDistance = 0 to 99999

	info.curveSide = 'left', 'right'

	info.centerDistance = -20 to 20 (negative is left)
	
	--------------------------------------------
	DO NOT PUT CODE BEFORE AND AFTER THIS JS OBJECT.
	YOU CAN CREATE OTHER ATTRIBUTES AND METHODS.
	THE OBJECT STATE IS PRESERVED BETWEEN CALLS
	--------------------------------------------
	
	*/
	pedal: '',
	wheel: '',
	
	play: function(info) {
		this.pedal = 'accelerate';

		if (info.centerDistance > 0) {
			this.wheel = 'left';
		
		} else if (info.centerDistance < 0) {
			this.wheel = 'right';
		
		} else {
			this.wheel = '';
		}
	}
}
