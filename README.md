# Astronomical Physics Calculator

A Java-based physics simulation program that models gravitational interactions between celestial bodies in 3D space.
Please read below if any definitions or terms are unfamiliar, I've done my best to explain them in the **Definitions** section.
<br>

## Features

- **N-Body Gravitational Simulation**: Simulate the motion of multiple celestial bodies under mutual gravitational attraction
- **Preset Scenarios**: Pre-configured simulations for Sun-Earth and Sun-Earth-Mars systems
- **Custom Simulations**: Create your own N-body systems with custom parameters
- **Energy Conservation Testing**: Built-in validation to ensure physical accuracy
- **Collision Detection**: Detects when bodies collide based on their radii
- **3D Space Simulation**: Full three-dimensional position and velocity tracking with orbital inclinations
- **Full Test suite**: JUnit tests created following TDD 
- **Euler Method**: Uses the most basic method at the moment which becomes proportionally worse dependent on the step size in the simulation


## Upcoming Features
- **2D & 3D UI**: Implementation of a user interface to correctly visualise the simulation
- **Runge-Katta Method:** Utilising a better, more performant and accurate method to calculate the simulation values as at the moment the Euler method is slow and inaccurate when N becomes increasingly large

<br>

## N-Body Simulator

### Overview
The N-body problem is a classical mechanics problem where you simulate N bodies (where N ≥ 2) and predict their motions under mutual gravitational attraction. While the two-body problem has an analytical solution, systems with three or more bodies (N ≥ 3) have no general closed-form solution and must be solved numerically using iterative methods.

### Physics Background

#### Newton's Law of Universal Gravitation (Two Bodies)
For two bodies, the gravitational force is:

$F = G \frac{m_1 m_2}{r^2}$

Where:
- $F$ = Gravitational force between two bodies
- $G \approx 6.674 \times 10^{-11} \text{ N(m/kg)}^2$ = Gravitational constant
- $m_1, m_2$ = Masses of the two bodies
- $r$ = Distance between the bodies

#### N-Body Problem Equation
For N bodies, the equation of motion for body $i$ is:

$m_i \frac{d²r_i}{dt²} = \sum_{j \neq i} G \frac{m_i m_j (r_j - r_i)}{|r_j - r_i|³}$

Where:
- $m_i$ = Mass of body $i$
- $r_i$ = Position vector of body $i$ (bold indicates vector quantity)
- $t$ = Time
- $\sum$ (sigma) = Summation over all other bodies
- The right side represents the net gravitational force from all other bodies

**Key Concept**: The force that one body feels from another equals the product of the gravitational constant, both masses, the inverse square of the distance, and the unit vector pointing from the first body toward the source.

#### Vectors vs Scalars
- **Vector**: Has both magnitude and direction (e.g., position, velocity, force)
- **Scalar**: Has only magnitude (e.g., mass, temperature, energy)

### Implementation Details

#### Numerical Integration
This simulator uses the **Euler method** for numerical integration:
1. Calculate gravitational forces between all body pairs
2. Compute accelerations using Newton's second law: $F = ma$
3. Update velocities: $v_{new} = v_{old} + a \cdot \Delta t$
4. Update positions: $r_{new} = r_{old} + v \cdot \Delta t$


#### Energy Conservation
The simulator validates physical accuracy by checking conservation of total energy:
- **Kinetic Energy**: $KE = \frac{1}{2}mv^2$
- **Potential Energy**: $PE = -G\frac{m_1 m_2}{r}$
- Usage within the Loop:

$U_{total} = \sum_{i} \sum_{j > i} -G \frac{m_i m_j}{r_{ij}}$

- **Total Energy**: $E_{total} = KE + PE$ (should remain constant in an isolated system)

#### Class Structure
- **`Body`** (abstract): Base class for all celestial bodies with position, velocity, mass, and force tracking
- **`Planet`**: Extends `Body` with planet-specific types (Gas Giant, Terrestrial, Dwarf, Ice Giant, Exoplanet)
- **`Star`**: Extends `Body` with star-specific types (Red Giant, Blue Giant, Brown Dwarf, White Dwarf, Neutron Star, Yellow Dwarf)
- **`CalculationEngine`**: Core physics calculations (forces, energy, collisions)
- **`CalculationBody`**: Simulation setup and preset scenarios

### Usage

#### Running Preset Simulations
1. Run the `Main` class
2. Choose from preset options:
   - **Option 1**: Sun and Earth (two-body system, 1 year simulation)
   - **Option 2**: Sun, Earth, and Mars (three-body system, 1 year simulation)
   - **Option 3**: Custom N-body simulation

#### Creating Custom Simulations
When selecting Option 3, you'll be prompted to enter:
- Number of bodies (N)
- Time step (Δt) in seconds
- Total simulation duration
- For each body:
  - Name (optional)
  - Mass (kg)
  - Initial position (x, y, z in meters)
  - Initial velocity (vx, vy, vz in m/s)
  - Radius (m)
  - Type (Planet or Star) and subtype

### Testing
The project includes JUnit tests in `CalculationTest.java`:
- **Energy Conservation Tests**: Verify total energy remains constant over time
- **Orbital Stability Tests**: Check that gravitational and centripetal forces balance for circular orbits
- **Collision Detection Tests**: Ensure bodies collide when their surfaces touch

<br>

### Definitions
- **Numerical Integration**: This is a way to solve problems _(differential equations)_ with no defined _closed form analytical solution,_ as N > 3 has no defined analytical solution we must approximate by changing the values we need to compute each time then update the values we are looking for.
  - **Closed-Form Analytical Solution**: This is an exact formula we can write using a set of defined functions which can be figured out in a single expression
  - **Differential Equations**: These are equations that allow us to find how something changes (derivatives) of the values we plug into our equations over time
- **Euler Method**: The Euler method is the most basic form of numerical integration used to solve differential equations. It works by taking a small time step (∆t) and updating the value using the current rate of change given by the derivative. The larger the time step, the more inaccurate the result becomes, because Euler assumes the rate of change stays constant during each step.
- **Sigma symbol:** $\sum_{i}$ - This similar to a for loop and produces a summative result (just means adding everything in the loop up)

[//]: # (### Rocket Trajectory Simulator)

[//]: # ()
[//]: # (#### **NOT YET IMPLEMENTED**)

[//]: # ()
[//]: # (### Nuclear Decay Simulator)

[//]: # ()
[//]: # (#### **NOT YET IMPLEMENTED**)

[//]: # ()
[//]: # (### Gravitational Lensing Calculator)

[//]: # ()
[//]: # (#### **NOT YET IMPLEMENTED**)
