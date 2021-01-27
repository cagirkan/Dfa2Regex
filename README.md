# Dfa2Regex
Convert given finite automata to regular expression (RE) with using Generalized Nondeterministic Finite Automaton(GNFA)
## Usage
Run java program and give the dfa.txt file's name.
> The file must in the same direction on your src file.

### Context About DFA.txt
```
S=q1
A=q2 (if more than one accept state, each of them should be seperated with comma(,))
E=a,b (for alphabet, seperated with comma(,))
Q=q1,q2 (for states, seperated with comma(,))
q1,a=q1 (for transaction function)
q1,b=q2
q2,a=q2
q2,b=q2
```
