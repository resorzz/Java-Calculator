# Java Calculator 🧮

![Calculator Preview](https://github.com/resorzz/Java-Calculator/blob/main/src/icon/icon.png)

Welcome to my Java Calculator project! This modern calculator application was developed as part of my first-year DAM (Desarrollo de Aplicaciones Multiplataforma) studies and was inspired by Bro Code's Java calculator tutorial.

## ✨ Project Description

This is a basic calculator application built with Java Swing, featuring a modern dark theme with purple accents, sound effects, and an operation history system. 

## 🚀 Features

### Core Operations
- **Basic Arithmetic**: Addition, subtraction, multiplication, and division
- **Advanced Functions**: Sign toggle (±), decimal point support
- **Error Handling**: Smart division by zero detection with audio feedback

### User Experience
- **Modern UI**: Dark theme with purple gradient design and rounded buttons
- **Sound System**: Different sound effects for buttons, operations, and errors
- **Operation History**: Stores up to 300 previous calculations with clickable results
- **Visual Feedback**: Hover effects and button animations
- **Expression Display**: Shows current operation in real-time

### Technical Highlights
- **Modular Architecture**: Clean separation of concerns across multiple classes
- **Memory Management**: Efficient audio resource handling with automatic cleanup
- **Input Validation**: Robust error checking and user input validation
- **Responsive Design**: Calculator layout with intuitive button placement

## 🛠️ Technical Stack

- **Language**: Java
- **GUI Framework**: Swing
- **Audio**: javax.sound.sampled
- **Architecture**: MVC Pattern

## 📁 Project Structure
src/
├── Calculator.java # Main UI and event handling
├── CalculatorFlow.java # State management and flow control
├── CalculatorOperations.java # Mathematical operations
├── History.java # Operation history management
├── SoundPlayer.java # Audio system
├── ui.java # Theme and styling
├── Principal.java # Application entry point
├── icon/
│ └── icon.png # Application icon
└── sounds/
├── button_soundeffect.wav # Button press sounds
├── equ_button_soundeffect.wav # Equals button sound
└── error_effect.wav # Error notification sound

## 🎯 Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, NetBeans, etc.)

### Installation & Running

1. **Clone the repository**
git clone https://github.com/resorzz/Java-Calculator.git
cd Java-Calculator


2. **Open in your IDE**
- Import the project into your preferred Java IDE
- Ensure all source files are in the `src` directory

3. **Run the application**
- Execute `Principal.java` as the main class
- Or compile and run from command line:
javac src/*.java
java -cp src Principal


## 🎮 How to Use

1. **Basic Operations**: Click number buttons and operators (+, -, ×, ÷)
2. **Calculate**: Press the equals button (=) to get results
3. **History**: Click any previous calculation in the history panel to reuse its result
4. **Clear**: Use 'C' to clear everything or 'Del' to delete the last character
5. **Sign Toggle**: Use '±' to change the sign of the current number

## 🎨 Features in Detail

### Sound System
- **Button Sounds**: Audible feedback for all button presses
- **Operation Sounds**: Special sound for successful calculations
- **Error Alerts**: Distinct audio warning for mathematical errors

### History Management
- Automatically saves all calculations
- Displays in reverse chronological order
- Click any result to continue calculating with that number
- Clear history option available

### Error Handling
- Division by zero protection
- Invalid input detection
- Graceful error recovery
- User-friendly error messages

## 🤝 Contributing

This is a student project, but contributions and suggestions are welcome! Feel free to:

- Report bugs by opening an issue
- Suggest new features
- Submit pull requests for improvements
- Share feedback on the code structure

## 📚 Learning Outcomes

This project helped me develop skills in:
- Java Swing GUI development
- Event-driven programming
- Object-oriented design patterns
- Audio programming in Java
- User experience design
- Error handling and validation

## 🙏 Acknowledgments

- **Bro Code** - Special thanks for the inspiring Java calculator tutorial that sparked this project
- **My DAM School** - For providing the learning environment and project requirements
- **Java Community** - For the extensive documentation and learning resources

## 📄 License

This project is open source and available under the [MIT License](LICENSE). Do whatever you want with it.

## 📞 Contact

Feel free to reach out if you have any questions or suggestions about this project!

---

**Note**: This calculator was developed as part of my first-year DAM studies and represents my journey in learning Java development. The code reflects educational purposes and continuous learning in software development.
